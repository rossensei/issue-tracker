import { defineConfig } from "kubb/config"
import { pluginTs } from "@kubb/plugin-ts"
import { pluginFetch } from "@kubb/plugin-fetch"
import { pluginVueQuery } from "@kubb/plugin-vue-query"
import { adapterOas } from "@kubb/adapter-oas"

export default defineConfig({
  root: ".",
  input: "http://localhost:8080/v3/api-docs",
  output: { 
    path: "./app/generated", 
    clean: true 
  },
  adapters: [
    adapterOas({
      validate: true,
      dateType: 'date',
    }),
  ],
  plugins: [
    pluginTs({ output: { path: "models", mode: "directory" } }),
    pluginFetch({
      output: {
        path: "clients",
        mode: "directory",
        barrel: {
          type: "named",
        }
      },
      baseURL: "http://localhost:8080",
      group: {
        type: "tag",
        name: ({ group }) => `${group}Service`,
      }
    }),
    pluginVueQuery({
      output: {
        path: "hooks",
        mode: "directory",
      },
      group: {
        type: "tag",
        name: ({ group }) => `${group}Hooks`,
      },
      client: "fetch",
      mutation: { methods: ["POST", "PUT", "DELETE"] },
      infinite: {
        queryParam: "page",
        initialPageParam: 0,
        nextParam: "number",
      },
      query: {
        methods: ["GET"],
        importPath: "@tanstack/vue-query",
      },
    })
  ]
});