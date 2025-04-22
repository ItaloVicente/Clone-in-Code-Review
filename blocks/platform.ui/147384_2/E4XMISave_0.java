		if (o instanceof MUIElement) {
			MUIElement uiElement = (MUIElement) o;
			if (OpaqueElementUtil.isOpaqueElement(uiElement) || RenderedElementUtil.isRenderedElement(uiElement)) {
				return;
			}
		}
