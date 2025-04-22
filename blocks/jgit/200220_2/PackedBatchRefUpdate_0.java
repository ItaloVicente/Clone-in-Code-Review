			PackedRefList oldPackedList = refdb.refreshPackedRefs();
			RefList<Ref> newRefs = applyUpdates(walk
			if (newRefs == null) {
				return;
			}

