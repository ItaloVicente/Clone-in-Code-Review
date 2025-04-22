	private void addShowInTags(MPerspective perspective) {
		if (perspective != null) {
			String targetId = getOriginalId(perspective.getElementId());
			ArrayList<String> showInTags = PerspectiveBuilder.getShowInPartFromRegistry(targetId);
			if (showInTags != null) {
				List<String> newTags = new ArrayList<>();
				for (String showIn : showInTags) {
					newTags.add(ModeledPageLayout.SHOW_IN_PART_TAG + showIn);
				}
				perspective.getTags().addAll(newTags);
			}
		}
	}

	private String getOriginalId(String id) {
		int index = id.lastIndexOf('.');
		if (index == -1)
			return id;
		return id.substring(0, index);
	}

