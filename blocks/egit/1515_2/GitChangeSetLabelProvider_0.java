
	}

	public StyledString getStyledText(Object element) {
		String rawText = getText(element);
		if (element.getClass().equals(GitModelCommit.class)) {
			StyledString string = new StyledString(rawText);
			GitModelCommit commit = (GitModelCommit) element;
			String format = " [" + getAbbreviatedId(commit) + "]"; //$NON-NLS-1$//$NON-NLS-2$
			string.append(format, StyledString.DECORATIONS_STYLER);
			return string;
		}

		return new StyledString(rawText);
	}

	private String getAbbreviatedId(GitModelCommit commit) {
		RevCommit remoteCommit = commit.getRemoteCommit();
		ObjectReader reader = commit.getRepository().newObjectReader();
		ObjectId commitId = remoteCommit.getId();
		AbbreviatedObjectId shortId;
		try {
			shortId = reader.abbreviate(commitId, 6);
		} catch (IOException e) {
			shortId = AbbreviatedObjectId.fromObjectId(ObjectId.zeroId());
			Activator.logError(e.getMessage(), e);
		} finally {
			reader.release();
		}
		return shortId.name();
