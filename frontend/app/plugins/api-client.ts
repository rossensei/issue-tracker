import { client } from "~/generated/.kubb/client"

export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig()

  client.setConfig({
    credentials: "include",
    baseURL: config.public.apiBaseUrl,
  })

  client.interceptors.request.use((request) => {
    request.credentials = 'include'
    return request
  })

  if (import.meta.server) {
    client.interceptors.request.use((request) => {
      const headers = useRequestHeaders(['cookie'])
      if (headers.cookie) {
        request.headers['Cookie'] = headers.cookie
      }
      return request
    })
  }
})