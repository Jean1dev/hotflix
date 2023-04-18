import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const baseUrl = "http://137.184.245.6:8080/api";

export const apiSlice = createApi({
  reducerPath: "api",
  tagTypes: ["Categories", "CastMembers", "Genres", "Videos"],
  endpoints: (_builder) => ({}),
  baseQuery: fetchBaseQuery({ baseUrl }),
});
