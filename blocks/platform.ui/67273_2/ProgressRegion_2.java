	private SideValue getLocation() {
		if (toolControl == null) {
			return SideValue.BOTTOM;
		}
		MElementContainer<?> parent = toolControl.getParent();
		while (parent != null) {
			if (parent instanceof MTrimBar) {
				return ((MTrimBar) parent).getSide();
			}
			parent = parent.getParent();
		}
		return SideValue.BOTTOM;
	}
