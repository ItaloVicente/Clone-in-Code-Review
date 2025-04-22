				dirty = true;
				int idx = cur.find(refName);
				if (idx >= 0) {
					cur = cur.set(idx, newRef);
				} else {
					cur = cur.add(idx, newRef);
