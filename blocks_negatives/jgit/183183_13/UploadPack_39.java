	private static Map<String, Ref> mapRefs(
			Map<String, Ref> refs, List<String> names) {
		return unmodifiableMap(
				names.stream()
					.map(refs::get)
					.filter(Objects::nonNull)
						.collect(toRefMap((a, b) -> b)));
