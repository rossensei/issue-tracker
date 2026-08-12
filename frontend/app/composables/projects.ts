import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query"

import { getAllProjects } from "~/generated/clients/getAllProjects"
import { storeProject } from "~/generated/clients/storeProject"
import type { StoreProjectRequest } from "~/generated/types/StoreProjectRequest"

export const useProjects = (page: Ref<number>, size: Ref<number>) => {
  const queryClient = useQueryClient()

  const projectsQuery = useQuery({
    queryKey: computed(() => ["projects", page.value, size.value]),
    queryFn: () =>
      getAllProjects({
        query: {
          page: page.value - 1,
          size: size.value,
        },
      }),
  })

  
  const createMutation = useMutation({
    mutationFn: (project: StoreProjectRequest) =>
      storeProject({
        body: project,
      }),

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['projects'],
      })
    },
  })

  return {
    data: computed(() => projectsQuery.data.value?.data.content ?? []),
    totalElements: computed(() => Number(projectsQuery.data.value?.data.totalElements) ?? 0),
    totalPages: computed(() => projectsQuery.data.value?.data.totalPages ?? 0),

    isLoading: projectsQuery.isLoading,
    isFetching: projectsQuery.isFetching,
    error: projectsQuery.error,

    createProject: createMutation.mutateAsync,
    updateProject: createMutation.mutateAsync,
    deleteProject: createMutation.mutateAsync,

    isCreating: createMutation.isPending,
    isUpdating: createMutation.isPending,
    isDeleting: createMutation.isPending,
  }
}