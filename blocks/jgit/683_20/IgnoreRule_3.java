package org.eclipse.jgit.ignore;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;

public class IgnoreRule {
	private String pattern;
	private boolean negation;
	private boolean nameOnly;
	private boolean dirOnly;
	private FileNameMatcher matcher;

	public IgnoreRule (String pattern) {
		this.pattern = pattern;
		negation = false;
		nameOnly = false;
		dirOnly = false;
		matcher = null;
		setup();
	}

	private void setup() {
		int startIndex = 0;
		int endIndex = pattern.length();
		if (pattern.startsWith("!")) {
			startIndex++;
			negation = true;
		}

		if (pattern.endsWith("/")) {
			endIndex --;
			dirOnly = true;
		}

		pattern = pattern.substring(startIndex

		if (!pattern.contains("/"))
			nameOnly = true;
		else if (!pattern.startsWith("/")) {
			pattern = "/" + pattern;
		}

		if (pattern.contains("*") || pattern.contains("?") || pattern.contains("[")) {
			try {
				matcher = new FileNameMatcher(pattern
			} catch (InvalidPatternException e) {
				e.printStackTrace();
			}
		}
	}


	public boolean getNameOnly() {
		return nameOnly;
	}

	public boolean dirOnly() {
		return dirOnly;
	}

	public boolean getNegation() {
		return negation;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isMatch(String target
		if (!target.startsWith("/"))
			target = "/" + target;

		if (matcher == null) {
			if (target.equals(pattern)) {
				if (dirOnly && !isDirectory)
					return false;
				else
					return true;
			}

			if ((target).startsWith(pattern + "/"))
				return true;

			if (nameOnly) {
				for (String folderName : target.split("/")) {
					if (folderName.equals(pattern))
						return true;
				}
			}

		} else {
			matcher.append(target);
			if (matcher.isMatch())
				return true;

			if (nameOnly) {
				for (String folderName : target.split("/")) {
					matcher.reset();
					matcher.append(folderName);
					if (matcher.isMatch())
						return true;
				}
			} else {
				matcher.reset();
				for (String folderName : target.split("/")) {
					if (folderName.length() > 0)
						matcher.append("/" + folderName);

					if (matcher.isMatch())
						return true;
				}
			}
		}

		return false;
	}

	public boolean getResult() {
		return !negation;
	}
}
