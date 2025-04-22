	void compareAndSetPackedRefs(PackedRefList curList
		if (packedRefs.compareAndSet(curList
				&& !curList.id.equals(newList.id)) {
			modCnt.incrementAndGet();
		}
	}

