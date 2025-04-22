		refdir = new WrappedRefDirectory(
				(RefDirectory) diskRepo.getRefDatabase()) {
			@Override
			public Map<String
				loadedRefsByPrefix = super.getRefs(prefix);
				return loadedRefsByPrefix;
			}
		};
