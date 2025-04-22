				oldPackedList = refdb.pack(locks);
			} else {
				oldPackedList = refdb.getPackedRefs();
			}
			RefList<Ref> newRefs = applyUpdates(walk, oldPackedList, pending);
			if (newRefs == null) {
				return;
