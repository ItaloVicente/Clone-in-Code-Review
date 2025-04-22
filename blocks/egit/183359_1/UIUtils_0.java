	public static void associateLabel(Control control, Label label) {
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {

			@Override
			public void getName(AccessibleEvent e) {
				e.result = label.getText();
			}
		});
	}

