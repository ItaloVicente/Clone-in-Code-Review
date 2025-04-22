		if (widget == null && parent != null) {
			int flags = SWT.PUSH;
			if (action != null) {
				if (action.getStyle() == IAction.AS_CHECK_BOX) {
					flags = SWT.TOGGLE;
				}
				if (action.getStyle() == IAction.AS_RADIO_BUTTON) {
					flags = SWT.RADIO;
				}
			}
