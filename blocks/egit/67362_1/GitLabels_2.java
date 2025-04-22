	public static @NonNull StyledString getChangedPrefix(
			@NonNull Repository repository) {
		StyledString string = new StyledString();
		if (RepositoryUtil.hasChanges(repository)) {
			string.append('>', StyledString.DECORATIONS_STYLER);
			string.append(' ');
		}
		return string;
	}

