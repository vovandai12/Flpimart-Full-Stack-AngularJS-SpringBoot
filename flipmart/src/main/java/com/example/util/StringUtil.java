package com.example.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
	public static String removeAccent(String string) {
		string = string.replaceAll(" ", "");
		String temp = Normalizer.normalize(string, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}
