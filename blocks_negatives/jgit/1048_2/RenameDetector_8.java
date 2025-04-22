		for (DiffEntry add : added) {
			Object del = map.remove(add.newId);
			if (del != null) {
				if (del instanceof DiffEntry) {
					entries.add(resolveRename(add, (DiffEntry) del,
							EXACT_RENAME_SCORE));
