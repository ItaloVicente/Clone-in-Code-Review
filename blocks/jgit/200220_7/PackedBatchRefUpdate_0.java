			try {
				PackedRefList oldPackedList = refdb.refreshPackedRefs();
				RefList<Ref> newRefs = applyUpdates(walk
				if (newRefs == null) {
					return;
				}
				refdb.commitPackedRefs(packedRefsLock
						true);
			} finally {
				packedRefsLock.unlock();
			}
