	private int deleteOverlappingSubtree(DirCacheEntry ent
		byte[] entPath = ent.path;
		int entLen = entPath.length;

		for (int p = pdir(entPath
			int i = findEntry(entPath
			if (i >= 0) {
				int n = --entryCnt - i;
				System.arraycopy(entries
				break;
			}

			i = -(i + 1);
			if (i < entryCnt && inDir(entries[i]
				break;
			}
		}

		int maxEnt = cache.getEntryCount();
		if (eIdx >= maxEnt) {
			return maxEnt;
		}

		DirCacheEntry next = cache.getEntry(eIdx);
		if (Paths.compare(next.path
				entPath
			insertEdit(new DeleteTree(entPath));
			return eIdx;
		}

		while (eIdx < maxEnt && inDir(cache.getEntry(eIdx)
			eIdx++;
		}
		return eIdx;
	}

	private int findEntry(byte[] p
		int low = 0;
		int high = entryCnt;
		while (low < high) {
			int mid = (low + high) >>> 1;
			int cmp = cmp(p
			if (cmp < 0) {
				high = mid;
			} else if (cmp == 0) {
				while (mid > 0 && cmp(p
					mid--;
				}
				return mid;
			} else {
				low = mid + 1;
			}
		}
		return -(low + 1);
	}

	private void insertEdit(DeleteTree d) {
		for (int i = editIdx; i < edits.size(); i++) {
			int cmp = EDIT_CMP.compare(d
			if (cmp < 0) {
				edits.add(i
				return;
			} else if (cmp == 0) {
				return;
			}
		}
		edits.add(d);
	}

	private static boolean inDir(DirCacheEntry e
		return e.path.length > pLen && e.path[pLen] == '/'
				&& peq(path
	}

	private static int pdir(byte[] path
		for (e--; e > 0; e--) {
			if (path[e] == '/') {
				return e;
			}
		}
		return 0;
	}

