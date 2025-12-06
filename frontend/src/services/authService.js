import api from "./api";

export const signUp = async (userData) => {
  const response = await api.post("/auth/signup", userData);
  return response.data;
}

export const login = async (loginData) => {
  const response = await api.post("/auth/signin", loginData);
  return response.data;
}

export const logout = async () => {
  const response = await api.get("/auth/logout");
  return response.data;
}
