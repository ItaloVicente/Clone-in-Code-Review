				Ref newRef = peeledPackedRef(oldRef);
				if (newRef == oldRef) {
					continue;
				}

				dirty = true;
