			for (int line = begin; line < end;) {
				RevCommit c = blame.getSourceCommit(line);
				String commit = abbreviate(c);
				String author = null;
				String date = null;
				if (!noAuthor) {
					author = author(line);
					date = date(line);
				}
				do {
					outw.print(commit);
					if (showSourcePath)
						outw.format(pathFmt
					if (showSourceLine)
						outw.format(numFmt
					if (!noAuthor)
						outw.format(authorFmt
					outw.format(lineFmt
					outw.flush();
					blame.getResultContents().writeLine(outs
					outs.flush();
					outw.print('\n');
				} while (++line < end && blame.getSourceCommit(line) == c);
