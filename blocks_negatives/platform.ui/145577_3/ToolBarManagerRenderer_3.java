		List<MToolBarElement> modelChildren = toolBar.getChildren();
		HashSet<MToolItem> oldModelItems = new HashSet<>();
		for (MToolBarElement itemModel : modelChildren) {
			if (OpaqueElementUtil.isOpaqueToolItem(itemModel)) {
				oldModelItems.add((MToolItem) itemModel);
			}
		}
