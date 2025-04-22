			PackedRefList oldPackedList;
			if (!refdb.isInClone()) {
				locks = lockLooseRefs(pending);
				if (locks == null) {
					return;
				}
				oldPackedList = refdb.pack(locks);
			} else {
				oldPackedList = refdb.getPackedRefs();
