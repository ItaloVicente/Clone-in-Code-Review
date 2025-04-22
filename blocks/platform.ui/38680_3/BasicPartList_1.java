
	private CTabItem findItemForPart(MUIElement element) {
		if (cTabFolder == null) {
			return null;
		}
		for (CTabItem cTabItem : cTabFolder.getItems()) {
			Object owningMe = cTabItem.getData(AbstractPartRenderer.OWNING_ME);
			if (owningMe == element || owningMe == element.getCurSharedRef()) {
				return cTabItem;
			}
		}
		return null;
	}
