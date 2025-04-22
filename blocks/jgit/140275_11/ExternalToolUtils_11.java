	public static Set<String> createSortedToolSet(String defaultName
			Set<String> userDefinedNames
		Set<String> names = new LinkedHashSet<>();
		if (defaultName != null) {
			Set<String> namesPredef = new LinkedHashSet<>();
			Set<String> namesUser = new LinkedHashSet<>();
			namesUser.addAll(userDefinedNames);
			namesUser.remove(defaultName);
			namesPredef.addAll(preDefinedNames);
			namesPredef.remove(defaultName);
			names.add(defaultName);
			names.addAll(namesUser);
			names.addAll(namesPredef);
		} else {
			names.addAll(userDefinedNames);
			names.addAll(preDefinedNames);
		}
		return names;
	}

