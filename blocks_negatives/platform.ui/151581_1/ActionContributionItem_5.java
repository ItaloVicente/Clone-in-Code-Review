		if (widget == null && parent != null) {
			int flags = SWT.PUSH;
			if (action != null) {
				int style = action.getStyle();
				switch (style) {
				case IAction.AS_CHECK_BOX:
					flags = SWT.CHECK;
					break;
				case IAction.AS_RADIO_BUTTON:
					flags = SWT.RADIO;
					break;
				case IAction.AS_DROP_DOWN_MENU:
					flags = SWT.DROP_DOWN;
					break;
				default:
					break;
				}
			}
