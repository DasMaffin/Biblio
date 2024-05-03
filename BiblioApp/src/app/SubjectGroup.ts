import * as SubjectGroupData from './SubjectGroups.json';
import { Injectable, signal } from "@angular/core";

export enum SubjectGroup{

}

export class SGHelper{    
    name: string;
    enum: string;
    
    constructor(name: string, _enum: string) {
        this.name = name;
        this.enum = _enum;
    }
}
@Injectable({
    providedIn: 'root'
})
export class SubjectGroupService{    
    public static AllSubjectGroups: SGHelper[] = [];

    public static LoadSubjectGroups(): void{
        this.AllSubjectGroups = SubjectGroupData.SubjectGroups.map((item: SGHelper) => {
            return new SGHelper(item.name, item.enum);
          });
    }

    public get staticAllSubjectGroups(){
        return SubjectGroupService.AllSubjectGroups;
    }

    public FindNameByEnum(_enum: SubjectGroup): string {
        if(!_enum || _enum.toString() == "") return "";

        let eString = _enum.toString();
        const foundGroup = SubjectGroupService.AllSubjectGroups.find(group => group.enum === eString);
        return foundGroup ? foundGroup.name : "";
    }

    // My try of getting the edit pre-selection work.
    public FindIndexByNameOrEnum(searchTerm: string | undefined): number | null {
        // Loop through each subject group in the list
        if(!searchTerm) return null;
        for (let i = 0; i < SubjectGroupService.AllSubjectGroups.length; i++) {
            const group = SubjectGroupService.AllSubjectGroups[i];
            // Check if the search term matches the name or enum of the current subject group
            if (group.name === searchTerm || group.enum === searchTerm) {
                // Return the index of the matching subject group
                return i;
            }
        }
        // If no match is found, return -1
        return -1;
    }
}