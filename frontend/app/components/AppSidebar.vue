<script setup lang="ts">
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from '@/components/ui/sidebar'
import  { IconDashboard, IconFileDescription, IconCalendar, IconSearch, IconSettings } from '@tabler/icons-vue'

// Menu items.
const items = [
  {
    title: 'Dashboard',
    url: '/',
    icon: IconDashboard,
  },
  {
    title: 'Projects',
    url: '/projects',
    icon: IconFileDescription,
  },
  {
    title: 'Calendar',
    url: '#',
    icon: IconCalendar,
  },
  {
    title: 'Search',
    url: '#',
    icon: IconSearch,
  },
  {
    title: 'Settings',
    url: '#',
    icon: IconSettings,
  },
]
</script>

<template>
  <Sidebar collapsible="icon" variant="sidebar">
    <SidebarContent>
      <SidebarGroup>
        <SidebarGroupLabel>Application</SidebarGroupLabel>
        <SidebarGroupContent>
          <SidebarMenu>
            <SidebarMenuItem v-for="item in items" :key="item.title">
              <SidebarMenuButton as-child>
                <NuxtLink :to="item.url">
                  <component :is="item.icon" />
                  <span>{{ item.title }}</span>
                </NuxtLink>
              </SidebarMenuButton>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarGroupContent>
      </SidebarGroup>
    </SidebarContent>
  </Sidebar>
</template>