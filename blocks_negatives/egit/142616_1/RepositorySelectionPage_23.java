			String text = (String) clipboard
					.getContents(TextTransfer.getInstance());
			if (text != null) {
				text = GitUrlChecker.sanitizeAsGitUrl(text);
				if (GitUrlChecker.isValidGitUrl(text)) {
					preset = text;
