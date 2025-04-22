	private Map<String
		try {
			return db.getRefDatabase().getRefs().stream().collect(
					Collectors.toMap(Ref::getName
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

