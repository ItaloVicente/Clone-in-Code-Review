	private MPart getPartToExtend(Control control) {
		MPart part = null;
		if (control == null) {
		} else {
			Control currentControl = control;
			MPart ownerPart = getOwnerPart(currentControl);
			while (currentControl != null && ownerPart == null) {
				currentControl = currentControl.getParent();
				ownerPart = getOwnerPart(currentControl);
			}
			part = ownerPart;
		}
		return part;
