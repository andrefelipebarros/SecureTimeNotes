import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  showTitlePrompt = false;
  showNoteEditor = false;
  newNoteTitle = '';
  newNoteContent = '';
  editingNote: any = null;

  notes: any[] = [];

  constructor(private authService: AuthService) {}

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  logout(): void {
    this.authService.logout();
    location.reload();
  }

  openNoteTitlePrompt() {
    this.showTitlePrompt = true;
    this.newNoteTitle = '';
  }

  confirmTitle() {
    if (this.newNoteTitle.trim()) {
      this.showTitlePrompt = false;
      this.showNoteEditor = true;
      this.newNoteContent = '';
    }
  }

  createNote() {
    if (this.newNoteTitle.trim() && this.newNoteContent.trim()) {
      this.notes.push({
        title: this.newNoteTitle,
        content: this.newNoteContent
      });
      this.resetNoteForm();
    }
  }

  editNote(note: any) {
    this.editingNote = note;
    this.newNoteTitle = note.title;
    this.newNoteContent = note.content;
    this.showNoteEditor = true;
  }

  saveEdit() {
    if (this.editingNote) {
      this.editingNote.title = this.newNoteTitle;
      this.editingNote.content = this.newNoteContent;
    }
    this.resetNoteForm();
  }

  cancelEdit() {
    this.resetNoteForm();
  }

  private resetNoteForm() {
    this.showNoteEditor = false;
    this.showTitlePrompt = false;
    this.newNoteTitle = '';
    this.newNoteContent = '';
    this.editingNote = null;
  }
}
