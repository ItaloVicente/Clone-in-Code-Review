package org.eclipse.egit.ui.internal.search;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.search.internal.core.text.PatternConstructor;

public abstract class PatternUtils {

	public static Pattern createPattern(String pattern,
			boolean isCaseSensitive, boolean isRegex)
			throws PatternSyntaxException {
		return PatternConstructor.createPattern(pattern, isCaseSensitive,
				isRegex);
	}

}
