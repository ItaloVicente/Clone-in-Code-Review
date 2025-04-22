			boolean reloadedPacks = false;
			COPYPACK: for (; ; ) {
				try {
					pin(pack

					int ptr = (int) (position - window.start);
					int n = (int) Math.min(window.size() - ptr
					window.write(out
					position += n;
					remaining -= n;
				} catch(Exception e){
					if (reloadedPacks) {
						throw new StoredPackRepresentationNotAvailableException(pack
					} else {
						reloadedPacks = true;
						WindowCache.purge(pack);
						continue COPYPACK;
					}
				}
				break COPYPACK;
			}
