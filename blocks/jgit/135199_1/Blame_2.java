				String pathFmt = MessageFormat.format(" %{0}s"
						valueOf(pathWidth));
				String numFmt = MessageFormat.format(" %{0}d"
						valueOf(1 + (int) Math.log10(maxSourceLine + 1)));
				String lineFmt = MessageFormat.format(" %{0}d) "
						valueOf(1 + (int) Math.log10(end + 1)));
				String authorFmt = MessageFormat.format(" (%-{0}s %{1}s"
						valueOf(authorWidth)

				for (int line = begin; line < end;) {
					RevCommit c = blame.getSourceCommit(line);
					String commit = abbreviate(reader
					String author = null;
					String date = null;
					if (!noAuthor) {
						author = author(line);
						date = date(line);
					}
					do {
						outw.print(commit);
						if (showSourcePath) {
							outw.format(pathFmt
						}
						if (showSourceLine) {
							outw.format(numFmt
									valueOf(blame.getSourceLine(line) + 1));
						}
						if (!noAuthor) {
							outw.format(authorFmt
						}
						outw.format(lineFmt
						outw.flush();
						blame.getResultContents().writeLine(outs
						outs.flush();
						outw.print('\n');
					} while (++line < end && blame.getSourceCommit(line) == c);
