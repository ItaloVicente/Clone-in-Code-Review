	Set<String> getAllAvailableSignatureAlgorithms() {
		Set<String> allAvailable = new HashSet<>();
		BuiltinSignatures.VALUES.forEach(s -> allAvailable.add(s.getName()));
		BuiltinSignatures.getRegisteredExtensions()
				.forEach(s -> allAvailable.add(s.getName()));
		return allAvailable;
	}

	private void setNewFactories(Collection<String> defaultFactories
			Collection<String> finalFactories) {
		Set<String> resultSet = new LinkedHashSet<>(defaultFactories);
		resultSet.addAll(finalFactories);
		setSignatureFactoriesNames(resultSet);
	}

