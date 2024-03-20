import axios from "axios";

const api = axios.create({
  baseURL: "http://127.0.0.1:8000",
  withCredentials: true,
  headers: {
    common: {
      Accept: "application/json",
    },
  },
});

// Add Bearer token from localStorage to every request
api.interceptors.request.use(
  async (config) => {
    config.headers["Authorization"] = `Bearer ${localStorage.getItem("token")}`;
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

export default api;
