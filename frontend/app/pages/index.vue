<!-- pages/dashboard.vue -->
<script setup lang="ts">
import { me } from '~/generated/clients/authService/index'

definePageMeta({
  middleware: "auth"
})

const { logout } = useAuth()
const { data: user, error } = await useAsyncData('me', () => me())

const handleLogout = async () => {
  await logout()
  await navigateTo('/login')
}
</script>

<template>
  <div v-if="user">Welcome, {{ user.data.username }}</div>
  <div v-else-if="error">Not logged in ({{ error }})</div>

  <Button @click="handleLogout">Logout</Button>
</template>