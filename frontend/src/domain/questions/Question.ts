import {QuestionType} from "./QuestionType";

export interface Question {
    id: number;
    number: string;
    name: string;
    blockName: string;

    media: string;
    questionType: QuestionType;
    points: number;
    categories: string[];
    law: string;
    lawDescription: string;
    securityThemOfQuestion: string;

    questionTextPL: string;
    answerATextPL: string;
    answerBTextPL: string;
    answerCTextPL: string;

    questionTextENG: string;
    answerATextENG: string;
    answerBTextENG: string;
    answerCTextENG: string;

    questionTextDE: string;
    answerATextDE: string;
    answerBTextDE: string;
    answerCTextDE: string;

    rightAnswer: string;

    knowType: string;
    bookmarked: boolean;
}