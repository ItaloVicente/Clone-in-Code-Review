package org.eclipse.e4.demo.contacts.model.internal;

import org.eclipse.e4.core.services.translation.TranslationService;

public class BinaryTranslatorProvider extends TranslationService {

	@Override
	public String translate(String key, String contributorURI) {
		if (key == null)
			return null;
		char[] charArray = key.toCharArray();
		StringBuffer tmp = new StringBuffer();
		tmp.append("0x");
		for(int i = 0; i < charArray.length; i++) {
			int value = charArray[i];
			tmp.append(Integer.toHexString(value));
		}
		return tmp.toString();
	}

}
