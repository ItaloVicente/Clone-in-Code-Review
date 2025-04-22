
	private CTabItem findItemForPart(MUIElement element) {
		CTabItem[] items = ctf.getItems();
		for (int i = 0; i < items.length; i++) {
			Object owningMe = items[i].getData(AbstractPartRenderer.OWNING_ME);
			if (owningMe == element || owningMe == element.getCurSharedRef()) {
				return items[i];
			}
		}
		return null;
	}
