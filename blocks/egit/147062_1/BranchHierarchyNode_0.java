		try {
			return getRepository().getRefDatabase()
					.getRefsByPrefix(getObject().toPortableString()).stream()
					.filter(ref -> !ref.isSymbolic())
					.collect(Collectors.toList());
		} catch (NoSuchFileException e) {
			return Collections.emptyList();
		}
