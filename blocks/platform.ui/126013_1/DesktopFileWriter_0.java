	public List<String> getRegisteredSchemes(Collection<String> schemes) {
		String mimeType = properties.get(KEY_MIME_TYPE);
		if (mimeType == null || mimeType.isEmpty()) {
			return Collections.emptyList();
		}
		Predicate<String> matchingSchemes = scheme -> {
			Util.assertUriSchemeIsLegal(scheme);
			String handlerPlusScheme = getHandlerPlusScheme(scheme);
			return mimeType.contains(handlerPlusScheme);
		};

		return schemes.stream().filter(matchingSchemes).collect(toList());
	}

