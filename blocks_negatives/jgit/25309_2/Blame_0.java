			for (int line = begin; line < end; line++) {
				outw.print(abbreviate(blame.getSourceCommit(line)));
				if (showSourcePath)
					outw.format(pathFmt, path(line));
				if (showSourceLine)
					outw.format(numFmt, valueOf(blame.getSourceLine(line) + 1));
				if (!noAuthor)
					outw.format(authorFmt, author(line), date(line));
				outw.format(lineFmt, valueOf(line + 1));
				outw.flush();
				blame.getResultContents().writeLine(outs, line);
				outs.flush();
				outw.print('\n');
