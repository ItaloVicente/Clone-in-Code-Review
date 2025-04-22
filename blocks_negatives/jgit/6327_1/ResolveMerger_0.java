				if (entry.getValue() != null) {
					createDir(f.getParentFile());
					DirCacheCheckout.checkoutEntry(db, f, entry.getValue(), r);
				} else {
					if (!f.delete())
						failingPaths.put(entry.getKey(),
								MergeFailureReason.COULD_NOT_DELETE);
				}
