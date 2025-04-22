	public static List<String> convertSchemes(Collection<ISchemeInformation> schemes) {
		return schemes.stream().map(s -> s.getScheme()).collect(toList());
	}

	public static List<ISchemeInformation> filter(Collection<ISchemeInformation> schemes, Collection<String> toFilter) {
		return schemes.stream().filter(s -> toFilter.contains(s.getScheme())).collect(toList());
	}	
	
