export interface Results {
  current_page: number;
  per_page: number;
  total: number;
  itens: Category[];
}

export interface Result extends Category {}

export interface Category {
  id: string;
  name: string;
  deleted_at: string;
  is_active: boolean;
  created_at: string;
  updated_at: string;
  description: null | string;
}

export interface Links {
  prev: string;
  last: string;
  next: string;
  first: string;
}

export interface Meta {
  to: number;
  from: number;
  path: string;
  total: number;
  per_page: number;
  last_page: number;
  current_page: number;
}

export interface CategoryParams {
  page?: number;
  perPage?: number;
  search?: string;
  isActive?: boolean;
}