
		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MToolBar)
				&& !(changedObj instanceof MMenu && !(changedObj instanceof MPopupMenu))) {
			return;
		}

		MUIElement container = modelService.getContainer((MUIElement) changedObj);
		if (container instanceof MPart) {
			MElementContainer<?> parent = ((MPart) container).getParent();
			if (parent instanceof MPartStack && parent.getSelectedElement() == container
					&& parent.getRenderer() == StackRenderer.this) {
				Object widget = parent.getWidget();
				if (widget instanceof CTabFolder) {
					adjustTopRight((CTabFolder) widget);
				}
			}
		}
