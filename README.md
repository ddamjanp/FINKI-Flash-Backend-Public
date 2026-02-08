# FINKI Flash ⚡  
### Generative AI Backend for Flashcard & Quiz Generation

FINKI Flash is a **Generative AI backend service** that automatically generates **flashcards** and **multiple-choice quiz questions** from academic course material using **LLM-based text generation**.

The system takes raw educational text (e.g. lecture notes or extracted PDF content) and produces **structured learning objects** that can be directly used in study or revision applications.

<h4>This project was made in collaboration with the following people:</h4>

<ul>
    <li>Sergej Norski</li>
    <li>Hristijan Deligjorgjiev</li>
    <li>Sara Apostolovska</li>
    <li>Vladimir Kambovski</li>
    <li>Dimitar Vergov</li>
    <li>Damjan Pushkovski</li>
    <li>Filip Ilievski</li>
    <li>Bojana Markovska</li>
    <li>Sara Kirovska</li>
    <li>Petar Kiceski</li>
</ul>

---
## This project:
- Uses **Large Language Model generation**
- Produces **novel content** 
- Generates **open-ended text**: questions, answers (1 correct, 3 false (for multiple choice))
- Outputs are **structured and validated** (Flashcards, QuizQuestions)
---

## Features

- Generate **flashcards** (question–answer pairs)
- Generate **quiz questions** (multiple choice with correct answer and explanation)
- Control generation parameters:
  - number of items
- Converts LLM output into domain objects:
  - `Flashcard`
  - `QuizQuestion`
- REST API interface for easy integration with frontend or other services

---


## High-level Architecture

1. User uploads a document via REST API
2. Text is extracted from the uploaded file
3. A constrained prompt is constructed from the extracted text (file is not stored in database)
4. The LLM generates structured JSON output
5. Output is parsed and converted into domain objects
6. Generated flashcards and quiz questions are returned (and can be persisted)



