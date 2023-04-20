import axios, { AxiosProgressEvent } from "axios";
import { baseUrl } from "../api/apiSlice";

export const getEndpoint = (id: string) => `${baseUrl}/videos/${id}/medias`;

export const formdata = (file: File) => {
  const data = new FormData();
  data.append('media_file', file);
  data.append("_method", "POST");
  data.append("Content-Type", "multipart/form-data");
  return data;
};

export const uploadProgress = (progressEvent: AxiosProgressEvent) => {
  if (progressEvent.total) {
    const progress = (progressEvent.loaded * 100) / progressEvent.total;
    return Math.round(progress * 100) / 100;
  }
  return 0;
};

export const uploadService = (params: {
  field: string;
  file: File;
  videoId: string;
  onUploadProgress?: (progressEvent: AxiosProgressEvent) => void;
}) => {
  const { field, file, videoId, onUploadProgress } = params;
  debugger
  const endpoint = `${getEndpoint(videoId)}/${field}`;
  const data = formdata(file);
  return axios.post(endpoint, data, { onUploadProgress });
};
