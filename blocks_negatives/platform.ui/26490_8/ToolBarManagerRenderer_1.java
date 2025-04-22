	/**
	 * Removes the IPresentationEngine.HIDDEN_EXPLICITLY from the trimbar
	 * entries. Having a separate logic for toolbars and toolcontrols would be
	 * confusing for the user, hence we remove this tag for both these types
	 *
	 * @param toolbarModel
	 */
	private void removeHiddenTags(MToolBar toolbarModel) {
		MWindow mWindow = modelService.getTopLevelWindowFor(toolbarModel);
		List<MTrimElement> trimElements = modelService.findElements(mWindow,
				null, MTrimElement.class, null);
		for (MTrimElement trimElement : trimElements) {
			trimElement.getTags().remove(IPresentationEngine.HIDDEN_EXPLICITLY);
		}
	}

