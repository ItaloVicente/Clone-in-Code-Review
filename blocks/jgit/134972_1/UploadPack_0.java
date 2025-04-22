	@NonNull
	private static Map<String
			Map<String
		return unmodifiableMap(
				names.stream()
					.map(refs::get)
					.filter(r -> r != null)
					.collect(toMap(Ref::getName
	}

