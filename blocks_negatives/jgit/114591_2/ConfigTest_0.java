		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape, 'b'), "\"x\\b\"");
		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape, 'n'), "\"x\\n\"");
		assertInvalidSubsection(
				MessageFormat.format(JGitText.get().badEscape, 't'), "\"x\\t\"");
