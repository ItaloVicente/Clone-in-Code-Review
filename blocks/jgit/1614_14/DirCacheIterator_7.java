		String pathString = currentEntry.getPathString();
		if (pathString != null) {
			if (pathString.endsWith(Constants.DOT_GIT_ATTRIBUTES))
				attributesNode = new LazyLoadingAttributesNode(
						currentEntry.getObjectId());
		}
