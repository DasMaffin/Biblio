import { Component, OnInit } from '@angular/core';
import { Book } from './book';
import { BookService } from './book.service';
import { NgForm } from '@angular/forms'
import { HttpErrorResponse } from '@angular/common/http';
import { LanguageService } from './language';
import { SubjectGroup, SubjectGroupService } from './SubjectGroup';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  public books: Book[] = [];
  public editBook!: Book | null | undefined;
  public deleteBook!: Book | null  | undefined;
  public totalCompensation: number = 0;  
  public releaseDateThreshold: Date = new Date('1990-01-01');
  public selectedSubjectGroup: string | null = null;

  constructor(private bookService: BookService, 
    public languageService: LanguageService, 
    public subjectGroupService: SubjectGroupService){}

  ngOnInit(): void {
      this.getBooks();
      LanguageService.LoadLanguages();
      SubjectGroupService.LoadSubjectGroups();
  }

  public onOpenModal(book: Book | null | undefined, mode: string): void{
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    button.setAttribute('data-target', '#' + mode + 'BookModal');
    this.editBook = book;
    this.deleteBook = book;

    container?.appendChild(button);
    button.click();

    if(mode == "edit" && book){
      this.selectedSubjectGroup = book.MainSubjectGroup?.toString() || null;
    }
  }

  // #region Basic CRUD operations

  public getBooks(): void{
    this.bookService.getBooks().subscribe(
      (response: Book[]) => {
        this.books = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddBook(addForm: NgForm): void{
    document.getElementById('add-book-form')?.click();

    this.cleanUp('AddBookModalBody');

    this.bookService.addBook(addForm.value).subscribe(
    (response: Book) => {
      console.log(response);
      this.getBooks();
    },
    (error: HttpErrorResponse) => {
      alert(error.message)
    });
  }

  public onEditBook(book: Book): void{
    console.log(book);
    this.bookService.updateBook(book).subscribe(
    (response: Book) => {
      console.log(response);
      this.getBooks();
    },
    (error: HttpErrorResponse) => {
      alert(error.message)
    });

  }
  public onDeleteBook(ISBN: string): void{
    this.bookService.deleteBook(ISBN).subscribe(
      ()=>{
        this.getBooks();
        alert("Book has been deleted!");
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  //#endregion

  // Clean any modal's data so it is empty on re-open.
  public cleanUp(modal: string): void{
    const inputFields = document.getElementById(modal)?.querySelectorAll('input, select');
    if(inputFields == null) alert("No Inputs");
    if (inputFields) 
    {
      inputFields.forEach((input: Element) => 
      {
        if (input.tagName.toLowerCase() === 'input') {
          (input as HTMLInputElement).value = '';
        } else if (input.tagName.toLowerCase() === 'select') {
          const selectElement = input as HTMLSelectElement;
          selectElement.selectedIndex = -1;
        }
      });
    }
  }

  // Get ISBN numbers without characters for openlibrary
  public RemoveCharacters(ISBN: string): string{
    return ISBN.replace('-', '');
  }

  public Compensation(book: Book): number{
    // Compensation = 100 (Base) * factor + extras
    // Base * factor = baseprice
    // factor:
    // < 50 Pages: 0,7
    // 50 – 99 Pages: 1
    // 100 – 199: 1,1
    // 200 – 299: 1,2
    // 300 – 499: 1,3
    // >= 500: 1,5
    // extras:
    // Books from before 1990: 15€
    // German books: +10% Baseprice
    if(book.Compensation != null && book.Compensation != 0) return book.Compensation;
    if(!book.Pages || !book.Language || !book.ReleaseDate || book.Pages <= 0){
      book.Compensation = 0;
      return book.Compensation;
    }
    let factor = 0.7;
    let DateExtra = 0;
    let LanguageExtra = 1;
    switch(true){
        case book.Pages >= 50 && book.Pages < 99:
            factor = 1;
            break;
        case book.Pages >= 100 && book.Pages < 199:
            factor = 1.1;
            break;
        case book.Pages >= 200 && book.Pages < 299:
            factor = 1.2;
            break;
        case book.Pages >= 300 && book.Pages < 499:
            factor = 1.3;
            break;
        case book.Pages >= 500:
            factor = 1.5;
            break;            
    }
    if(book.Language == LanguageService.FindLanguageByCode('de')) LanguageExtra = 1.1;

    if(this.checkReleaseDate(book.ReleaseDate)) DateExtra = 15;
    book.Compensation = 100 * factor * LanguageExtra + DateExtra;
    return book.Compensation;
  }

  sortColumn: string = ''; // Store the currently sorted column
  sortDirection: number = 1; // Store the sorting direction (1 for ascending, -1 for descending)

  sortTable(column: string) {
    if (column === this.sortColumn) {
      this.sortDirection = -this.sortDirection; // Reverse the sorting direction if the same column is clicked
    } else {
      this.sortColumn = column; // Update the sort column
      this.sortDirection = 1; // Reset the sorting direction to ascending
    }
  
    // Sort the books array based on the selected column and direction
    this.books.sort((a, b) => {
      // Check for empty fields
      if (!a[column] && !b[column]) {
        return 0; // Both are empty, maintain their current order
      } else if (!a[column]) {
        return 1; // Only a is empty, move it to the bottom
      } else if (!b[column]) {
        return -1; // Only b is empty, move it to the bottom
      }
  
      // Compare non-empty values
      if (isNaN(a[column])) {
        return a[column].localeCompare(b[column]) * this.sortDirection;
      } else {
        return (a[column] - b[column]) * this.sortDirection;
      }
    });
  }


  updateTotalCompensation() {
    this.totalCompensation = this.books
      .filter(book => book.Selected)
      .reduce((total, book) => {
        return total + this.Compensation(book);
      }, 0);
  }

  toggleCheckbox(book: Book) {
    book.Selected = !book.Selected;
    this.updateTotalCompensation();
  }

  getFixedGradientColor(pages: number): string {
    if (pages <= 49) {
      return 'rgb(255, 0, 0)'; // Red
    } else if (pages >= 50 && pages <= 99) {
      return 'rgb(204, 51, 0)'; // Dark Orange
    } else if (pages >= 100 && pages <= 199) {
      return 'rgb(173, 82, 0)'; // Burnt Orange
    } else if (pages >= 200 && pages <= 299) {
      return 'rgb(141, 114, 0)'; // Dark Goldenrod
    } else if (pages >= 300 && pages <= 499) {
      return 'rgb(99, 156, 0)'; // Olive Drab
    } else {
      return 'rgb(0, 255, 0)'; // Lime Green
    }
  }

  checkReleaseDate(dateString: Date): boolean{
    if(!dateString) return false;
    let date = new Date(dateString); // Why is book.ReleaseDate string, I explicitly made it a Date
    if(date < this.releaseDateThreshold) return true;
    return false;
  }
  title = 'Biblio';
}
