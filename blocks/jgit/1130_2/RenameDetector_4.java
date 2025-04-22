				DiffEntry best = bestPathMatch(a
				if (best != null) {
					best.changeType = ChangeType.RENAME;
					entries.add(exactRename(best
				} else {
					left.add(a);
