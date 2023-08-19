package com.mokhnachev.prawaruchu.questions.persistance.questions.xml;

import com.mokhnachev.prawaruchu.questions.domain.Category;
import com.mokhnachev.prawaruchu.questions.domain.Question;
import com.mokhnachev.prawaruchu.questions.domain.QuestionType;
import com.mokhnachev.prawaruchu.questions.domain.RightAnswer;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
public class ExcelReader {

    private static final int QUESTION_NAME_CELL = 0;
    private static final int QUESTION_NUMBER_CELL = 1;

    private static final int QUESTION_TEXT_PL_CELL = 2;
    private static final int QUESTION_ANSWER_A_PL_CELL = 3;
    private static final int QUESTION_ANSWER_B_PL_CELL = 4;
    private static final int QUESTION_ANSWER_C_PL_CELL = 5;

    private static final int QUESTION_TEXT_ENG_CELL = 6;
    private static final int QUESTION_ANSWER_A_ENG_CELL = 7;
    private static final int QUESTION_ANSWER_B_ENG_CELL = 8;
    private static final int QUESTION_ANSWER_C_ENG_CELL = 9;

    private static final int QUESTION_TEXT_DE_CELL = 10;
    private static final int QUESTION_ANSWER_A_DE_CELL = 11;
    private static final int QUESTION_ANSWER_B_DE_CELL = 12;
    private static final int QUESTION_ANSWER_C_DE_CELL = 13;

    private static final int RIGHT_ANSWER_CELL = 14;

    private static final int MEDIA_ID_CELL = 15;

    private static final int QUESTION_TYPE_CELL = 16;

    private static final int QUESTION_POINTS_CELL = 17;

    private static final int CATEGORIES_CELL = 18;

    private static final int BLOCK_NAME_CELL = 19;

    private static final int LAW_CELL = 20;

    private static final int LAW_DESC_CELL = 21;

    private static final int SECURITY_THEME_CELL = 22;

    private Question parseRow(Row row) {
        Question q = new Question();
        q.setName(String.valueOf(row.getCell(QUESTION_NAME_CELL)));
        q.setNumber(String.valueOf(row.getCell(QUESTION_NUMBER_CELL)));
        q.setBlockName(String.valueOf(row.getCell(BLOCK_NAME_CELL)));

        q.setQuestionTextPL(String.valueOf(row.getCell(QUESTION_TEXT_PL_CELL)));
        q.setAnswerATextPL(String.valueOf(row.getCell(QUESTION_ANSWER_A_PL_CELL)));
        q.setAnswerBTextPL(String.valueOf(row.getCell(QUESTION_ANSWER_B_PL_CELL)));
        q.setAnswerCTextPL(String.valueOf(row.getCell(QUESTION_ANSWER_C_PL_CELL)));

        q.setQuestionTextENG(String.valueOf(row.getCell(QUESTION_TEXT_ENG_CELL)));
        q.setAnswerATextENG(String.valueOf(row.getCell(QUESTION_ANSWER_A_ENG_CELL)));
        q.setAnswerBTextENG(String.valueOf(row.getCell(QUESTION_ANSWER_B_ENG_CELL)));
        q.setAnswerCTextENG(String.valueOf(row.getCell(QUESTION_ANSWER_C_ENG_CELL)));

        q.setQuestionTextDE(String.valueOf(row.getCell(QUESTION_TEXT_DE_CELL)));
        q.setAnswerATextDE(String.valueOf(row.getCell(QUESTION_ANSWER_A_DE_CELL)));
        q.setAnswerBTextDE(String.valueOf(row.getCell(QUESTION_ANSWER_B_DE_CELL)));
        q.setAnswerCTextDE(String.valueOf(row.getCell(QUESTION_ANSWER_C_DE_CELL)));

        String rightAnswerStr = String.valueOf(row.getCell(RIGHT_ANSWER_CELL));
        q.setRightAnswer(RightAnswer.valueOf(rightAnswerStr));

        q.setMedia(String.valueOf(row.getCell(MEDIA_ID_CELL)));

        String questionTypeStr = String.valueOf(row.getCell(QUESTION_TYPE_CELL));
        if (questionTypeStr.equals("PODSTAWOWY")) {
            q.setQuestionType(QuestionType.COMMON);
        }
        if (questionTypeStr.equals("SPECJALISTYCZNY")) {
            q.setQuestionType(QuestionType.SPECIAL);
        }
        if (q.getQuestionType() == null) {
            throw new RuntimeException();
        }

        q.setPoints(Integer.parseInt(row.getCell(QUESTION_POINTS_CELL).getStringCellValue()));

        String[] categoriesStrArr = row.getCell(CATEGORIES_CELL).getStringCellValue().split(",");
        List<Category> categories = Stream.of(categoriesStrArr).map(Category::valueOf).toList();
        q.setCategories(categories);

        q.setLaw(row.getCell(LAW_CELL).getStringCellValue());

        q.setLawDescription(row.getCell(LAW_DESC_CELL).getStringCellValue());

        q.setSecurityThemOfQuestion(row.getCell(SECURITY_THEME_CELL).getStringCellValue());

        return q;
    }

    public List<Question> parseQuestionsXML(String xmlPath) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File(xmlPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = workbook.getSheetAt(0);

        List<Question> result = new ArrayList<>();
        for (Row row : sheet) {
            String firstCellStr = row.getCell(0).getStringCellValue();
            if (firstCellStr.equals("Nazwa pytania")) {
                continue;
            }
            if (firstCellStr.isBlank()) {
                continue;
            }

            result.add(parseRow(row));
        }

        return result;
    }

}
