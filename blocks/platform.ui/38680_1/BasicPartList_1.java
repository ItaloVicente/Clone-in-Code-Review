
	private CTabItem findItemForPart(MUIElement element) {
		MElementContainer<MUIElement> stack = element.getParent();
		if (stack == null) {
			return null;
		}

		CTabFolder ctf = (CTabFolder) stack.getWidget();
		if (ctf == null) {
			return null;
		}

		CTabItem[] items = ctf.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getData(AbstractPartRenderer.OWNING_ME) == element) {
				return items[i];
			}
		}
		return null;
	}
