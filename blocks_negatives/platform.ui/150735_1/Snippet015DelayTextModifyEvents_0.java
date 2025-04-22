		text2.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				delayed2.setValue(text2.getText());
			}
		});

