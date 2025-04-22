		Predicate<String> matchingSchemes = scheme -> {
			Util.assertUriSchemeIsLegal(scheme);
			String handlerPlusScheme = getHandlerPlusScheme(scheme);
			return mimeType.contains(handlerPlusScheme);
		};

		return schemes.stream().filter(matchingSchemes).collect(toList());
