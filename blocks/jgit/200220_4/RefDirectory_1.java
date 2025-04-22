			PackedRefList newList = result != null ? result : NO_PACKED_REFS;
			if (packedRefs.compareAndSet(curList
					&& !curList.id.equals(newList.id)) {
				modCnt.incrementAndGet();
			}
			return newList;
