import { Note } from './note';
import { NoteFilter } from './note-filter';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

const headers = new HttpHeaders().set('Accept', 'application/json');

@Injectable()
export class NoteService {
  noteList: Note[] = [];
  api = `${environment.apiUrl}/api/notes`;
  size$ = new BehaviorSubject<number>(0);

  constructor(private http: HttpClient) {
  }

  findById(id: string): Observable<Note> {
    const url = `${this.api}/${id}`;
    const params = { id };
    return this.http.get<Note>(url, {params, headers});
  }

  load(filter: NoteFilter): void {
    this.find(filter).subscribe(result => {
        this.noteList = result;
      },
      err => {
        console.error('error loading', err);
      }
    );
  }

  find(filter: NoteFilter): Observable<Note[]> {
    const params: any = {
      title: filter.title,
      sort: `${filter.column},${filter.direction}`,
      size: filter.size,
      page: filter.page
    };
    if (!filter.direction) { delete params.sort; }

    const userNotes = `${environment.apiUrl}/user/notes`;
    return this.http.get(userNotes, {params, headers}).pipe(
      map((response: any) => {
        this.size$.next(response.totalElements);
        return response.content;
      })
    );
  }

  save(entity: Note): Observable<Note> {
    let params = new HttpParams();
    let url = '';
    if (entity.id) {
      url = `${this.api}/${entity.id.toString()}`;
      params = new HttpParams().set('ID', entity.id.toString());
      return this.http.put<Note>(url, entity, {headers, params});
    } else {
      url = `${this.api}`;
      return this.http.post<Note>(url, entity, {headers, params});
    }
  }

  delete(entity: Note): Observable<Note> {
    let params = new HttpParams();
    let url = '';
    if (entity.id) {
      url = `${this.api}/${entity.id.toString()}`;
      params = new HttpParams().set('ID', entity.id.toString());
      return this.http.delete<Note>(url, {headers, params});
    }
    return null;
  }
}

