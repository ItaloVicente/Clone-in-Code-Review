			try {
				long p = lastPack.findOffset(this, t);
				if (0 < p) {
					r.add(new FoundObject<T>(t, lastIdx, lastPack, p));
					it.remove();
					continue;
