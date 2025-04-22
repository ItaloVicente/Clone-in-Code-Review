			blame = BlameResult.create(generator);
			begin = 0;
			end = blame.getResultContents().size();
			if (rangeString != null)
				parseLineRangeOption();
			blame.computeRange(begin, end);

			int authorWidth = 8;
			int dateWidth = 8;
			int pathWidth = 1;
			int maxSourceLine = 1;
			for (int line = begin; line < end; line++) {
				RevCommit c = blame.getSourceCommit(line);
				if (c != null && !c.has(scanned)) {
					c.add(scanned);
					if (autoAbbrev)
						abbrev = Math.max(abbrev, uniqueAbbrevLen(c));
					authorWidth = Math.max(authorWidth, author(line).length());
					dateWidth = Math.max(dateWidth, date(line).length());
					pathWidth = Math.max(pathWidth, path(line).length());
