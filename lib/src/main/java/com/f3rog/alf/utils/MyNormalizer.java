package com.f3rog.alf.utils;

import java.text.Normalizer;

/**
 * Class {@link MyNormalizer}
 *
 * @author f3rog
 */
public class MyNormalizer {

	/**
	 * Normalize given text. Turn it to upper case, delete all non-ASCII-code characters
	 */
	public static String normalize(String text) {
		text = text.toUpperCase();
		text = Normalizer.normalize(text, Normalizer.Form.NFKD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		text = text.replaceAll(" ", "");
		return text;
	}

	public static String normalizeForFile(String text) {
		text = text.toLowerCase();
		text = Normalizer.normalize(text, Normalizer.Form.NFKD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		text = text.replaceAll(" ", "-");
		return text;
	}

}
