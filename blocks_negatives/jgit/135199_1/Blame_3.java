				do {
					outw.print(commit);
					if (showSourcePath)
						outw.format(pathFmt, path(line));
					if (showSourceLine)
						outw.format(numFmt, valueOf(blame.getSourceLine(line) + 1));
					if (!noAuthor)
						outw.format(authorFmt, author, date);
					outw.format(lineFmt, valueOf(line + 1));
					outw.flush();
					blame.getResultContents().writeLine(outs, line);
					outs.flush();
					outw.print('\n');
				} while (++line < end && blame.getSourceCommit(line) == c);
