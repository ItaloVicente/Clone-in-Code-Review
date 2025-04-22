	public List<String> getRegisteredSchemes(Collection<String> schemes) {
		Predicate<String> matchingSchemes = scheme -> {
			Util.assertUriSchemeIsLegal(scheme);
			return getExistingElementFor(scheme) != null;
		};

		return schemes.stream().filter(matchingSchemes).collect(toList());
	}

