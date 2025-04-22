				DiffEntry best = null;
				for (DiffEntry e : list) {
					if (sameType(e.oldMode, dst.newMode)) {
						best = e;
						break;
					}
