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
				p.getPackFile().getAbsolutePath()));
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		buf.append('\n');
		buf.append(sw.toString());
		System.err.println(buf.toString());
	}

