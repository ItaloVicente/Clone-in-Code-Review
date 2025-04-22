				RevCommit c = blame.getSourceCommit(line);
				if (c != null && !c.has(scanned)) {
					c.add(scanned);
					if (autoAbbrev)
						abbrev = Math.max(abbrev
					authorWidth = Math.max(authorWidth
					dateWidth = Math.max(dateWidth
					pathWidth = Math.max(pathWidth
				}
				while (line + 1 < end && blame.getSourceCommit(line + 1) == c)
					line++;
