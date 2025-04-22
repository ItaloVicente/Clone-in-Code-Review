	private void handlePackError(IOException e
		String tmpl;
		if ((e instanceof CorruptObjectException)
				|| (e instanceof PackInvalidException)) {
			tmpl = JGitText.get().corruptPack;
			removePack(p);
		} else {
			tmpl = JGitText.get().exceptionWhileReadingPack;
		}
		StringBuilder buf = new StringBuilder(MessageFormat.format(tmpl
