import { login as apiLogin, me as apiMe, logout as apiLogout } from "~/generated/clients/authService/index"
import type { AuthenticatedUser } from "~/generated/models/AuthenticatedUser"

export const useAuth = () => {
  const user = useState<AuthenticatedUser | null>("auth-user", () => null)
  const isLoggedIn = computed(() => user.value !== null)

  const login = async (username: string, password: string) => {
    await apiLogin({ body: { username, password } })
  }

  const fetchUser = async () => {
    try {
      const { data } = await useAsyncData("me", () => apiMe())
      user.value = data.value?.data ?? null
    } catch (error) {
      console.error("Failed to fetch user data:", error)
    }
  }

  const logout = async () => {
    await apiLogout()
    user.value = null
  }

  return { user, isLoggedIn, login, fetchUser, logout }
}