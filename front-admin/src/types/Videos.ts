import { CastMember } from "./CastMembers";
import { Category } from "./Category";
import { Genre } from "./Genres";

export type FileObject = {
  name: string;
  file: File;
};

export interface Results {
  current_page: number;
  per_page: number;
  total: number;
  itens: Video[];
}

export interface Result extends Video {}

export interface Video {
  id: string;
  title: string;
  description: string;
  year_launched: string;
  opened: boolean;
  rating: string;
  duration: string;
  deleted_at?: string;
  created_at: string;
  updated_at: string;
  genres?: Genre[];
  categories?: Category[];
  cast_members?: CastMember[];
  thumb_file_url: string;
  banner_file_url: string;
  trailer_file_url: string;
  video_file_url: string;
}

export interface VideoParams {
  page?: number;
  perPage?: number;
  search?: string;
  isActive?: boolean;
}

export interface VideoPayload {
  id?: string;
  title: string;
  rating: string;
  opened: boolean;
  duration: string;
  description: string;
  published: boolean;
  genres?: string[];
  year_launched: string;
  categories?: string[];
  cast_members?: string[];
}
