import { SubjectGroup } from "./SubjectGroup";

export class Book{
    ISBN: string;
    Title: string;
    ReleaseDate: Date;
    Pages: number;
    Language: string;
    MainSubjectGroup: SubjectGroup;
    SubSubjectGroup: SubjectGroup;
    Compensation: number = 0;
    Selected: boolean = false;

    [key: string]: any;

    public get GetReleaseDate(){
        return new Date(this.ReleaseDate);
    }

    constructor(
        _ISBN: string,
        _Title: string,
        _ReleaseDate: Date,
        _Pages: number,
        _Language: string,
        _MainSubjectGroup: SubjectGroup,
        _SubSubjectGroup: SubjectGroup
    ){
        this.ISBN = _ISBN;
        this.Title = _Title;
        this.ReleaseDate = _ReleaseDate;
        this.Pages = _Pages;
        this.Language = _Language;
        this.MainSubjectGroup = _MainSubjectGroup;
        this.SubSubjectGroup = _SubSubjectGroup;
    }
}