	protected void writeContextLine(final OutputStream out
			final int line
		writeLine(out
	}

	private boolean isEndOfLineMissing(final RawText text
		return line + 1 == text.size() && text.isMissingNewlineAtEnd();
	}

	protected void writeAddedLine(final OutputStream out
			throws IOException {
		writeLine(out
	}

	protected void writeRemovedLine(final OutputStream out
			final int line
		writeLine(out
	}

	protected void writeHunkHeader(final OutputStream out
			int bStartLine
