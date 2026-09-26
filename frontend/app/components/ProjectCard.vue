<template>
  <Card>
    <CardHeader>
      <CardTitle>{{ project.name }}</CardTitle>
      <CardDescription>{{ project?.owner?.username }}</CardDescription>
    </CardHeader>
    <CardContent>
      <p>{{ project.description }}</p>
    </CardContent>
    <CardFooter>
      <p><IconLink /></p>
    </CardFooter>
  </Card>
</template>

<script setup lang="ts">
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { IconLink } from "@tabler/icons-vue"
import type { ProjectResponse } from '~/generated/models/ProjectResponse'

const props = defineProps<{
  project: ProjectResponse
}>()
</script>
