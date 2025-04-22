		if (parent == null) {
			parent = element.getTransientData().get(RENDER_PARENT);
			if (parent != null) {
				Object widget = safeCreateGui(element, parent, parentContext);
				if (element.isVisible()) {
					if (widget instanceof Menu) {
						((Control) parent).setMenu((Menu) widget);
					}
				}
				return widget;
			}
		}

