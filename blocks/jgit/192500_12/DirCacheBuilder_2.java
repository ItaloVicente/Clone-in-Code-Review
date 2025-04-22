	public void remove(DirCacheEntry entry){
		entriesToRemove.add(entry);
	}
	private void removeForTesting(){
		for(int i = 0 ; i < entryCnt; i++){
			if(i%2==0){
				remove(entries[i]);
			}
		}
	}

	private void removeEntries() {
		Collections.sort(entriesToRemove
		final DirCacheEntry[] n = new DirCacheEntry[entryCnt];
		int i = 0;
		int j = 0;
		int ins = 0;
		while (i < entryCnt) {
			int cmp =
					j < entriesToRemove.size() ? DirCache.ENT_CMP.compare(entries[i]
							: -1;
			if (cmp < 0) {
				n[ins++] = entries[i++];
			} else if (cmp > 0) {
				j++;
			} else {
				i++;
				j++;
			}
		}
		entries = n;
		entryCnt = ins;
	}

