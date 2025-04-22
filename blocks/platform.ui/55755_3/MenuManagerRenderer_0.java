			if (element instanceof MPopupMenu && element.isVisible()) {
				Object data = getUIContainer(element);
				if (data instanceof Control && parent.equals(data)) {
					((Control) parent).setMenu(newMenu);
				}
			}
