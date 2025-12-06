import api from "./api";

export const getUser = async () => {
  const response = await api.get("/user/me");
  return response.data;
}

export const postUserData = async (userData) => {
  const response = await api.post("/user/data", userData);
  return response.data;
}

export const deleteUser = async () => {
  const response = await api.delete("user/delete");
  return response.data;
}