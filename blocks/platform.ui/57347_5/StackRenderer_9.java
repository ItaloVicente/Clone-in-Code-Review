
			if (eventTabItem != null) {
				MUIElement uiElement = (MUIElement) eventTabItem.getData(AbstractPartRenderer.OWNING_ME);
				MPart tabPart = (MPart) ((uiElement instanceof MPart) ? uiElement
						: ((MPlaceholder) uiElement).getRef());
				openMenuFor(tabPart, tabFolder, absolutePoint);
			}
