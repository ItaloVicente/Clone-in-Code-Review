
	public static List<String> convertSchemes(Collection<Scheme> schemes) {
		return schemes.stream().map(s -> s.getScheme()).collect(toList());
	}

	public static List<Scheme> filter(Collection<Scheme> schemes, Collection<String> toFilter) {
		return schemes.stream().filter(s -> toFilter.contains(s.getScheme())).collect(toList());
	}
}
