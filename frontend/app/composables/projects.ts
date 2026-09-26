import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query"

import { getAllProjects, storeProject } from "~/generated/clients/index"
import type { StoreProjectRequest } from "~/generated/models/StoreProjectRequest"

export const useProjects = () => {
  const queryClient = useQueryClient()

  const projectsQuery = useQuery({
    queryKey: computed(() => ["projects"]),
    queryFn: () => getAllProjects(),
  });

  
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
    data: computed(() => projectsQuery.data.value ?? []),

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
