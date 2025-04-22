		return abbrevations.computeIfAbsent(objectId, id -> {
			try {
				return reader.abbreviate(id).name();
			} catch (IOException cannotAbbreviate) {
				return id.name();
			}
		});
