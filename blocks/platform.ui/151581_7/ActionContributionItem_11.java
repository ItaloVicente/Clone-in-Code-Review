		if ((style & (SWT.TOGGLE | SWT.CHECK)) != 0) {
			if (action.getStyle() == IAction.AS_CHECK_BOX) {
				action.setChecked(selection);
			}
		} else if ((style & SWT.RADIO) != 0) {
			if (action.getStyle() == IAction.AS_RADIO_BUTTON) {
				action.setChecked(selection);
			}
		} else if ((style & SWT.DROP_DOWN) != 0) {
			if (e.detail == SWT.ARROW) { // on drop-down button
				if (action.getStyle() == IAction.AS_DROP_DOWN_MENU) {
					IMenuCreator mc = action.getMenuCreator();
					menuCreatorCalled = true;
					ToolItem ti = (ToolItem) item;
					if (mc != null) {
						Menu m = mc.getMenu(ti.getParent());
						if (m != null) {
							Point point = ti.getParent().toDisplay(new Point(e.x, e.y));
							m.setLocation(point.x, point.y); // waiting
							m.setVisible(true);
							return; // we don't fire the action
