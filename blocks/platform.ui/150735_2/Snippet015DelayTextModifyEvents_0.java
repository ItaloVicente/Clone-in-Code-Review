

		text2.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				dbc.updateTargets();
			}
		});

		text1.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				delayed1.setValue(text1.getText());
			}
		});
