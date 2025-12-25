import axios from "axios";

const api = axios.create({
  baseURL:"http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
})


// Add a token to every request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if(token){
      config.headers.Authorization = `Bearer ${token}`;
    }
    // console.log("Request config:", config);
    return config;
  },
  (error) => {
    // console.log("Request error:", error);
    return Promise.reject(error);
  }
)

//Handle token expiration
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    console.log("Response error:", error);
    const originalRequest = error.config;

    if (error.response.data.message === "Authentication token is invalid or expired" && error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        
        const response = await axios.post("http://localhost:8080/auth/refresh", {}, {withCredentials: true});
        const newAccessToken = response.data.accessToken;
        localStorage.setItem('accessToken', newAccessToken);
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (err) {
        localStorage.removeItem('accessToken');
        window.location.href = '/login';
        return Promise.reject(err);
      }
    }

    return error;
    // return Promise.reject(error);
  }
)

export default api;

