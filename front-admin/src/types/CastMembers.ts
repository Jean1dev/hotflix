export interface Results {
  current_page: number;
  per_page: number;
  total: number;
  itens: CastMember[];
}

export interface Result extends CastMember {}

export interface CastMember {
  id: string;
  name: string;
  type: string;
  deleted_at: null;
  created_at: string;
  updated_at: string;
}

export interface CastMemberParams {
  page?: number;
  perPage?: number;
  search?: string;
  type?: number;
}
