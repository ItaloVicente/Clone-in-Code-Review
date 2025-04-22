
	public Map<AnyObjectId
		return getGraftsDatabase().getGrafts();
	}

	public Map<AnyObjectId
		Map<String
				Constants.R_REPLACE);
		Map<AnyObjectId
				replaceRefs.size());
		for (Entry<String
			replacements.put(ObjectId.fromString(e.getKey().substring(
					Constants.R_REPLACE.length() + 1))
					.getObjectId());
		}
		return replacements;
	}
