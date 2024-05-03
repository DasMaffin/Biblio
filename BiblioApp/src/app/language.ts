import * as languagesData from './languages.json';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class LanguageService{
    public static AllLanguages: Language[];

    public static LoadLanguages(): void{
        this.AllLanguages = languagesData.Languages.map((item: Language) => {
            return new Language(item.code, item.name);
          });
    }

    public static FindLanguageByCode(code: string): string | undefined {
        return this.AllLanguages.find(language => language.code === code)?.name;
    }

    public static FindCodeByName(name: string): string | undefined {
        const foundLanguage = this.AllLanguages.find(language => language.name === name);
        return foundLanguage ? foundLanguage.code : undefined;
    }

    public get staticAllLanguages(){
        return LanguageService.AllLanguages;
    }
}

export class Language{
    code: string;
    name: string;


    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
}