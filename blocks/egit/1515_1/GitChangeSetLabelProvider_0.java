
	}

	public StyledString getStyledText(Object element) {
		String rawText = getText(element);
		if (element.getClass().equals(GitModelCommit.class)) {
			GitModelCommit commit = (GitModelCommit) element;
			StyledString string = new StyledString(rawText);
			String commitId = commit.getRemoteCommit().getName();
			String format = " [" + commitId.substring(0, 6) + "]";  //$NON-NLS-1$//$NON-NLS-2$
			string.append(format, StyledString.DECORATIONS_STYLER);
			return string;
		}

		return new StyledString(rawText);
