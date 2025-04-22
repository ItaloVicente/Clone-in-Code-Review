			List<String> tags;
			if (info.itemElement instanceof MPlaceholder) {
				tags = ((MPlaceholder) info.itemElement).getRef().getTags();
			} else {
				tags = info.itemElement.getTags();
			}
			if (tags.contains(IPresentationEngine.NO_MOVE)) {
