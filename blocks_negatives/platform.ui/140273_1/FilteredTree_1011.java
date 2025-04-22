			clearButton.getAccessible().addAccessibleControlListener(
				new AccessibleControlAdapter() {
					@Override
					public void getRole(AccessibleControlEvent e) {
						e.detail= ACC.ROLE_PUSHBUTTON;
					}
