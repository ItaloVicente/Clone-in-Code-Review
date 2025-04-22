
		if (prefix.equals(HEAD)) {
			return Optional.ofNullable(refDb.exactRef(HEAD)).stream()
					.collect(Collectors.toUnmodifiableList());
		}

