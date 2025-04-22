	private String getTagsString() {
		StringBuilder sb = new StringBuilder();
		Map<String, Ref> tagsMap = db.getTags();
		for (String tagName : tagsMap.keySet()) {
			ObjectId peeledId = tagsMap.get(tagName).getPeeledObjectId();
			if (peeledId != null && peeledId.equals(commit)) {
				if (sb.length() > 0)
					sb.append(", "); //$NON-NLS-1$
				sb.append(tagName);
			}
		}

		return sb.toString();
	}

	private String formatHeadRef(Ref ref) {
		final String name = ref.getName();
		if (name.startsWith(Constants.R_HEADS))
			return name.substring(Constants.R_HEADS.length());
		else if (name.startsWith(Constants.R_REMOTES))
			return name.substring(Constants.R_REMOTES.length());
		return name;
	}

	private String formatTagRef(Ref ref) {
		final String name = ref.getName();
		if (name.startsWith(Constants.R_TAGS))
			return name.substring(Constants.R_TAGS.length());
		return name;
	}

