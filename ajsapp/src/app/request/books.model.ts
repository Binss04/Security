export class Book {
    id : number;
    title: string;
    author: string;
    genre: string;
    language: string;
    pagecount: number;
    price: number;
    publish_date: Date;
    publisher: string;
    synopsis: string;
  
    constructor(
      id: number,
      title: string,
      author: string,
      genre: string,
      language: string,
      pagecount: number,
      price: number,
      publish_date: Date,
      publisher: string,
      synopsis: string
    ) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.genre = genre;
      this.language = language;
      this.pagecount = pagecount;
      this.price = price;
      this.publish_date = publish_date;
      this.publisher = publisher;
      this.synopsis = synopsis;
    }
  }
  