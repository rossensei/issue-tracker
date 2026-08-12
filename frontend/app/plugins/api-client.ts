// app/plugins/api.ts

import createClient from 'openapi-fetch'
import type { paths } from '~~/types/api'

export default defineNuxtPlugin(() => {
  const config = useRuntimeConfig()

  const api = createClient<paths>({
    baseUrl: config.public.apiBaseUrl,
  })

  return {
    provide: {
      api,
    },
  }
})