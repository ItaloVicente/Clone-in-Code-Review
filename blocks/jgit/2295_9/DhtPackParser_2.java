		linkIterators[segments - 1] = objectListByName
				.subList(beginIdx

		boolean inserted = true;
		while (inserted) {
			inserted = false;
			for (ListIterator<DhtInfo> itr : linkIterators) {
				int cnt = 0;
				while (itr.hasNext() && cnt < linkBatchSize) {
					DhtInfo oe = itr.next();
							ObjectIndexKey.create(repo
							oe.info(chunkOf(oe.chunkPtr))
							dbWriteBuffer);
					cnt++;
				}
				if (0 < cnt) {
					pm.update(cnt);
					inserted = true;
				}
			}
		}

		pm.endTask();
	}

	@SuppressWarnings("unchecked")
	private static ListIterator<DhtInfo>[] newListIteratorArray(int size) {
		return new ListIterator[size];
