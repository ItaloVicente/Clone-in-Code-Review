			for (Ref ref : repo.getRefDatabase().getRefsByPrefixes(prefixes)) {
				addRef(ref
			}
		} else if (refs == null) {
			for (Ref ref : repo.getRefDatabase()
					.getRefsByPrefix(Constants.R_REFS)) {
				addRef(ref
			}
		}
