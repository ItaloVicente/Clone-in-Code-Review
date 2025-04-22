			clearButton.getAccessible().addAccessibleListener(
					new AccessibleAdapter() {
						@Override
						public void getName(AccessibleEvent e) {
							e.result = E4DialogMessages.FilteredTree_AccessibleListenerClearButton;
						}
					});
			clearButton.getAccessible().addAccessibleControlListener(
					new AccessibleControlAdapter() {
						@Override
						public void getRole(AccessibleControlEvent e) {
							e.detail = ACC.ROLE_PUSHBUTTON;
						}
					});
