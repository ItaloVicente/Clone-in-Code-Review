		messageLabel.getAccessible().addAccessibleAttributeListener(
				new AccessibleAttributeAdapter() {
					public void getAttributes(AccessibleAttributeEvent e) {
						e.attributes = new String[] { "container-live", //$NON-NLS-1$
								"polite", "live", "polite",   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
								"container-live-role", "status", }; //$NON-NLS-1$ //$NON-NLS-2$
					}
				});
