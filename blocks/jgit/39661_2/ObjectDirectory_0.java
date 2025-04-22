	private static void logCorruptPackError(IOException e
		StringBuilder buf = new StringBuilder(MessageFormat.format(
				JGitText.get().exceptionWhileReadingPack
				p.getPackFile().getAbsolutePath()));
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		buf.append('\n');
		buf.append(sw.toString());
		System.err.println(buf.toString());
	}

