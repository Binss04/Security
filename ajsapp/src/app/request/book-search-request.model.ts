export interface BookSearchRequest {
    id: number
    author: string;
    genre: string;
    language: string;
    startDate: Date | null; 
    endDate: Date | null;
    pageNo: number;
    pageSize: number;
  }
  
  