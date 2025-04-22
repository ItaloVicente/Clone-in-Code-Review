			} else if (f == null || !m.idEqual(i)) {
				update(m.getEntryPathString()
						m.getEntryFileMode());
			} else if (i.getDirCacheEntry() != null) {
				if (f.isModified(i.getDirCacheEntry()
						|| i.getDirCacheEntry().getStage() != 0)
					update(m.getEntryPathString()
							m.getEntryFileMode());
				else
					keep(i.getDirCacheEntry());
