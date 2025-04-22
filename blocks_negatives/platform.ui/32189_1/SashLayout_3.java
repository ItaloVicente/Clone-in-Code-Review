
	private List<MUIElement> getVisibleChildren(MGenericTile<?> sashContainer) {
		List<MUIElement> visKids = new ArrayList<MUIElement>();
		for (MUIElement child : sashContainer.getChildren()) {
			if (child.isToBeRendered() && child.isVisible())
				visKids.add(child);
		}
		return visKids;
	}

	private static int getWeight(MUIElement element) {
		String info = element.getContainerData();
		if (info == null || info.length() == 0) {
			return 0;
		}

		try {
			int value = Integer.parseInt(info);
			return value;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
