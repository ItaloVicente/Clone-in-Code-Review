		createMenuItem(menu, SWTRenderersMessages.menuDetach, e -> detachActivePart(menu));

	}

	private void detachActivePart(final Menu menu) {
		MPart selectedPart = (MPart) menu.getData(STACK_SELECTED_PART);
		CTabItem cti = findItemForPart(selectedPart);
		if (cti == null || cti.getParent() == null) {
			return;
		}
		CTabFolder parent = cti.getParent();

		EModelService modelService = getContextForParent(selectedPart).get(EModelService.class);
		Rectangle bounds = parent.getBounds();
		Point display = parent.toDisplay(bounds.x, bounds.y);
		modelService.detach(selectedPart, display.x, display.y, bounds.width, bounds.height);
