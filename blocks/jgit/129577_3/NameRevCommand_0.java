			for (Ref ref : repo.getRefDatabase()
					.getRefsByPrefix(prefixes.toArray(new String[0]))) {
				addRef(ref
			}
		} else if (refs == null) {
			for (Ref ref : repo.getRefDatabase()
					.getRefsByPrefix(Constants.R_REFS)) {
				addRef(ref
			}
		}
