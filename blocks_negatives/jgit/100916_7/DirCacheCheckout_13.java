
			for (Map.Entry<String, CheckoutMetadata> e : updated.entrySet()) {
				String path = e.getKey();
				CheckoutMetadata meta = e.getValue();
				DirCacheEntry entry = dc.getEntry(path);
				if (!FileMode.GITLINK.equals(entry.getRawMode()))
					checkoutEntry(repo, entry, objectReader, false, meta);
