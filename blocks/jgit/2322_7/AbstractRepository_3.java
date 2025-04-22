
	public Map<AnyObjectId
		return getGraftsDatabase().getGrafts();
	}

	public Map<AnyObjectId
		Map<String
		try {
			replaceRefs = getRefDatabase().getRefs(
					Constants.R_REPLACE);
		} catch (IOException e1) {
		}
		Map<AnyObjectId
				replaceRefs.size());
		for (Entry<String
			replacements.put(ObjectId.fromString(e.getKey())
					.getObjectId());
		}
		return replacements;
	}
