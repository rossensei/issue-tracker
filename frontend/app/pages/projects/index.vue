<template>
  <div class="flex flex-col gap-4">
    <h1 class="scroll-m-20 text-start text-2xl font-bold tracking-tight text-balance">
      Projects
    </h1>
    <p class="text-gray-600">This is the projects page.</p>

    <!-- <div class="grid grid-cols-4 gap-4">
      <ProjectCard v-for="project in projects" :key="project.id" :project="project" />
      <div v-for="project in projects" :key="project.id" class="rounded-lg border p-4">
        <h2 class="text-lg font-semibold">
          <NuxtLink :to="`/projects/${project.id}`">
            {{ project.name }}
          </NuxtLink>
        </h2>
        <p class="text-gray-600">{{ project.description }}</p>
      </div>
    </div> -->

    <!-- <div class="flex flex-col gap-6">
      <Pagination 
        v-slot="{ page }" 
        v-model:page="currentPage"
        :items-per-page="itemsPerPage" 
        :default-page="1"
      >
        <PaginationContent v-slot="{ items }">
          <PaginationPrevious />
          <template v-for="(item, index) in items" :key="index">
            <PaginationItem
              v-if="item.type === 'page'"
              :value="item.value"
              :is-active="item.value === page"
            >
              {{ item.value }}
            </PaginationItem>
          </template>
          <PaginationEllipsis :index="4" />
          <PaginationNext />
        </PaginationContent>
      </Pagination>
    </div> -->
  </div>
</template>

<script lang="ts" setup>
// import {
//   Pagination,
//   PaginationContent,
//   PaginationEllipsis,
//   PaginationItem,
//   PaginationNext,
//   PaginationPrevious,
// } from '@/components/ui/pagination'

// const { data: projects } = useProjects()
</script>
