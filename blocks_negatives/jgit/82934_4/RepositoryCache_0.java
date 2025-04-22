		for (int stage = 0; stage < 2; stage++) {
			for (Iterator<Map.Entry<Key, Reference<Repository>>> i = cacheMap
					.entrySet().iterator(); i.hasNext();) {
				final Map.Entry<Key, Reference<Repository>> e = i.next();
				final Repository db = e.getValue().get();
				if (db != null)
					db.close();
				i.remove();
			}
