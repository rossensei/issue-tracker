<!-- pages/login.vue -->
<script setup lang="ts">
definePageMeta({
  layout: false,
})

const { login } = useAuth()
const route = useRoute()

const username = ref('')
const password = ref('')
const error = ref<string | null>(null)

const handleLogin = async () => {
  error.value = null
  try {
    await login(username.value, password.value)
    const redirect = route.query.redirect
    await navigateTo(typeof redirect === "string" ?  redirect : "/")
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Login failed'
  }
}
</script>

<template>
  <form @submit.prevent="handleLogin">
    <input v-model="username" placeholder="username" />
    <input v-model="password" type="password" placeholder="password" />
    <button type="submit">Log in</button>
    <p v-if="error">{{ error }}</p>
  </form>
</template>