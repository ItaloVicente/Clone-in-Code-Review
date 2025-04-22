			final PackedRefList packed = getPackedRefs();
			RefList<Ref> cur = readPackedRefs();

			boolean dirty = false;
			for (String refName : refs) {
				Ref oldRef = readRef(refName, cur);
				if (oldRef == null) {
				}
				if (oldRef.isSymbolic()) {
				}
				Ref newRef = peeledPackedRef(oldRef);
				if (newRef == oldRef) {
					continue;
				}
