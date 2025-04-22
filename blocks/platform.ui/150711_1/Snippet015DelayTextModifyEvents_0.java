				.observeDelayed(2000, text2);

		text2.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				delayed2.setValue(text2.getText());
			}
		});
