			String pathFmt = MessageFormat.format(" %{0}s", valueOf(pathWidth)); //$NON-NLS-1$
			String numFmt = MessageFormat.format(" %{0}d", //$NON-NLS-1$
					valueOf(1 + (int) Math.log10(maxSourceLine + 1)));
			String lineFmt = MessageFormat.format(" %{0}d) ", //$NON-NLS-1$
					valueOf(1 + (int) Math.log10(end + 1)));
			String authorFmt = MessageFormat.format(" (%-{0}s %{1}s", //$NON-NLS-1$
					valueOf(authorWidth), valueOf(dateWidth));

			for (int line = begin; line < end;) {
				RevCommit c = blame.getSourceCommit(line);
				String commit = abbreviate(c);
				String author = null;
				String date = null;
				if (!noAuthor) {
					author = author(line);
					date = date(line);
