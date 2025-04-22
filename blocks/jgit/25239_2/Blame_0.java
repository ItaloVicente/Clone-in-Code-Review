				RevCommit c = blame.getSourceCommit(line);
				if (c.has(scanned))
					continue;
				c.add(scanned);

				if (autoAbbrev)
					abbrev = Math.max(abbrev
