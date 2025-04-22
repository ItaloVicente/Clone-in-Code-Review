		Clipboard clipboard = new Clipboard(parent.getDisplay());
		String clipText = (String) clipboard.getContents(TextTransfer
				.getInstance());
		final String pattern = "git fetch (\\w+:\\S+) (refs/changes/\\d+/\\d+/\\d+) && git (\\w+) FETCH_HEAD"; //$NON-NLS-1$
		Matcher matcher = Pattern.compile(pattern).matcher(clipText);
		String defaultUri = null;
		String defaultCommand = null;
		String defaultChange = null;
		if (matcher.matches()) {
			defaultUri = matcher.group(1);
			defaultChange = matcher.group(2);
			defaultCommand = matcher.group(3);
		}
