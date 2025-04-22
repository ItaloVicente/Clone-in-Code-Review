			} else if (del != null) {
				List<DiffEntry> list = (List<DiffEntry>) del;
				DiffEntry best = null;
				for (DiffEntry e : list) {
					if (best == null && sameType(e.oldMode
						best = e;
				}
				if (best != null) {
					if (best.changeType == ChangeType.DELETE) {
						best.changeType = ChangeType.RENAME;
						entries.add(exactRename(best
					} else {
						entries.add(exactCopy(best
					}
