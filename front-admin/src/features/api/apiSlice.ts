import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const baseUrl = "https://hotflix-admin.herokuapp.com/api";

export const apiSlice = createApi({
  reducerPath: "api",
  tagTypes: ["Categories", "CastMembers", "Genres", "Videos"],
  endpoints: (_builder) => ({}),
  baseQuery: fetchBaseQuery({ baseUrl }),
});
