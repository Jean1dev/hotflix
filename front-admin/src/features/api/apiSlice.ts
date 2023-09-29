import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {keycloak} from "../../config/keycloackConfig";

export const baseUrl = "http://localhost:8080/api"//"https://hotflix-admin.herokuapp.com/api";

export const apiSlice = createApi({
  reducerPath: "api",
  tagTypes: ["Categories", "CastMembers", "Genres", "Videos"],
  endpoints: (_builder) => ({}),
  baseQuery: fetchBaseQuery({
    baseUrl,
    prepareHeaders: (headers) => {
      if (keycloak.token) {
        headers.set("Authorization", `Bearer ${keycloak.token}`)
      }

      return headers
    }
  }),
});
