
	private void setChildVisible(MToolBarElement modelItem, IContributionItem item,
			IContributionManager manager) {
		Boolean currentChildVisible = isChildVisible(item, manager);
		Boolean prevChildVisible = (Boolean) modelItem.getTransientData().get(PREV_CHILD_VISIBLE);

		if (currentChildVisible != null) {
			if (prevChildVisible == null) {
				modelItem.getTransientData().put(PREV_CHILD_VISIBLE, modelItem.isVisible());
				modelItem.setVisible(currentChildVisible);
			}
		} else if (prevChildVisible != null) {
			modelItem.setVisible(prevChildVisible);
			modelItem.getTransientData().remove(PREV_CHILD_VISIBLE);
		}
	}

	private Boolean isChildVisible(IContributionItem item, IContributionManager manager) {
		Boolean v;
		IContributionManagerOverrides overrides = manager.getOverrides();
		if (overrides == null) {
			v = null;
		} else {
			v = overrides.getVisible(item);
		}

		if (v != null) {
			return v.booleanValue();
		}
		return null;
	}
