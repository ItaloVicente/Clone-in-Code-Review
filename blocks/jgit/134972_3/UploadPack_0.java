	@NonNull
	private static Map<String
			Map<String
		return unmodifiableMap(
				names.stream()
					.map(refs::get)
					.filter(Objects::nonNull)
					.collect(toMap(Ref::getName
	}

