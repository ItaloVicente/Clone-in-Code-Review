			if (!skipGarbagePack(lastPack)) {
				try {
					long p = lastPack.findOffset(this
					if (0 < p) {
						r.add(new FoundObject<T>(t
						it.remove();
						continue;
					}
				} catch (IOException e) {
