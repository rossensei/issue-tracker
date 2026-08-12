import { defineConfig } from "kubb/config"
import { pluginTs } from "@kubb/plugin-ts"
import { pluginFetch } from "@kubb/plugin-fetch"
import { pluginVueQuery } from "@kubb/plugin-vue-query"

export default defineConfig({
  root: ".",
  input: "http://localhost:8080/v3/api-docs",
  output: { 
    path: "./app/generated", 
    clean: true 
  },
  plugins: [
    pluginTs({
      output: {
        path: "types",
        mode: "directory",
      }
    }),
    pluginFetch({
      baseURL: "http://localhost:8080",
      output: {
        path: "clients",
        mode: "directory",
      }
    }),
    pluginVueQuery({
      output: {
        path: "hooks",
        mode: "directory",
      },
    })
  ]
})