			removed = filterOut(removed
			nonDeleted = null;
			Iterator<Map.Entry<String
					.entrySet().iterator();
			Map.Entry<String
			try {
				while (toUpdate.hasNext()) {
					e = toUpdate.next();
					String path = e.getKey();
					CheckoutMetadata meta = e.getValue();
					DirCacheEntry entry = dc.getEntry(path);
					if (!FileMode.GITLINK.equals(entry.getRawMode())) {
						checkoutEntry(repo
					}
					e = null;
				}
			} catch (Exception ex) {
				if (e != null) {
					toUpdate.remove();
				}
				while (toUpdate.hasNext()) {
					e = toUpdate.next();
					toUpdate.remove();
				}
				throw ex;
			}
