	private Map<String
		try {
			return db.getRefDatabase().getRefsByPrefix(RefDatabase.ALL).stream()
					.collect(Collectors.toMap(Ref::getName
							Function.identity()));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

