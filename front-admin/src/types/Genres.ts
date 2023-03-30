import { Category } from "./Category";

export interface Genres {
  current_page: number;
  per_page: number;
  total: number;
  itens: Genre[];
}

export interface Result extends Genre {}

export interface Genre {
  id: string;
  name: string;
  isActive: boolean;
  deleted_at: null;
  created_at: string;
  updated_at: string;
  categories_id?: string[];
  categories?: Category[];
  description?: null | string;
  pivot?: Pivot;
}

export interface Pivot {
  genre_id: string;
  category_id: string;
}

export interface GenreParams {
  page?: number;
  perPage?: number;
  search?: string;
  isActive?: boolean;
}

export interface GenrePayload {
  id: string;
  name: string;
  categories_id?: string[];
}
