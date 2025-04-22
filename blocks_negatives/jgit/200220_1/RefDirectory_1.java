		final PackedRefList newList = readPackedRefs();
		if (packedRefs.compareAndSet(curList, newList)
				&& !curList.id.equals(newList.id)) {
			modCnt.incrementAndGet();
		}
		return newList;
