			if (element instanceof MPopupMenu && element.isVisible()) {
				Object data = menuModel.getTransientData().get(IPresentationEngine.RENDERING_PARENT_KEY);
				if (data instanceof Control && parent.equals(data)) {
					((Control) parent).setMenu(newMenu);
				}
			}
