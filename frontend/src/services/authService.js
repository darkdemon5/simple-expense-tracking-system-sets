import api from "./api";

export const signUp = async (userData) => {
  const response = await api.post("/auth/signup", userData);
  return response.data;
};

export const login = async (loginData) => {
  // const response = await api.post("/auth/signin", loginData);
  // try {
    const response = await api.post("/auth/signin", loginData);
    console.log("Login response in authService:", response);
    return response;
  // } catch (error) {
  //   // const status = error.response?.status;
  //   // const body = error.response?.data;
  //   console.log("Login error in authService:", error);
  //   return error;
  // }
  // console.log("Login response data:", response.data);
  // console.log("Login response: ", response);
  // return response.data;
};

export const logout = async () => {
  const response = await api.get("/auth/logout");
  return response.data;
};
