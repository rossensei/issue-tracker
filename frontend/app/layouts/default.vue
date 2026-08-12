<template>
  <SidebarProvider>
    <AppSidebar />
    <main class="relative w-full min-h-screen p-6">
      <SidebarTrigger class="absolute top-1/2 -translate-y-1/2" />
      <slot />
    </main>
  </SidebarProvider>
</template>

<script setup lang="ts">
import AppSidebar from '@/components/AppSidebar.vue'
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar'
</script>
