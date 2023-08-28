package com.mokhnachev.prawaruchu;

import com.mokhnachev.prawaruchu.questions.domain.Category;
import com.mokhnachev.prawaruchu.questions.domain.Question;
import com.mokhnachev.prawaruchu.questions.domain.QuestionType;
import com.mokhnachev.prawaruchu.questions.persistance.questions.xml.ExcelReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = TestConfig.class)
class PrawaruchuApplicationTests {

	@Value("${application.questions_xml_path}")
	private String mediaDatabaseDirectoryPath;

	@Test
	void contextLoads() {
		ExcelReader excelReader = new ExcelReader();
		List<Question> allQuestions = excelReader.parseQuestionsXML(mediaDatabaseDirectoryPath);

		List<Question> questions = allQuestions.stream()
				.filter(question ->
						question.getCategories().stream().anyMatch(category -> category.equals(Category.A))
				)
				.filter(question ->
						question.getQuestionType().equals(QuestionType.SPECIAL)
				)
				.toList();

		Map<String, Integer> result = new HashMap<>();

		for (Question question : questions) {
			List<String[]> strs = List.of(
					question.getQuestionTextPL().split(" "),
					question.getAnswerATextPL().split(" "),
					question.getAnswerBTextPL().split(" "),
					question.getAnswerCTextPL().split(" ")
			);

			for (String[] strrs : strs) {
				for (String str : strrs) {
					String a = str.trim().replaceAll("[^a-zA-ZćśźżęąłĆŚŻŹĘĄŁ]", "");
					if (a.isEmpty() || a.length() == 1) continue;
                    result.merge(a, 1, Integer::sum);
				}
			}
		}

		Map<String, Integer> stringIntegerMap = sortByValue(result);
	}

	public static <K, Integer extends Comparable<? super java.lang.Integer>> Map<K, java.lang.Integer> sortByValue(Map<K, java.lang.Integer> map) {
		List<Map.Entry<K, java.lang.Integer>> list = new ArrayList<>(map.entrySet());
		list.sort((Comparator<Map.Entry<K, java.lang.Integer>> & Serializable) (c1, c2) -> c1.getValue().equals(c2.getValue()) ? 0 : c1.getValue() < c2.getValue() ? 1 : -1);

		Map<K, java.lang.Integer> result = new LinkedHashMap<>();
		for (Map.Entry<K, java.lang.Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
