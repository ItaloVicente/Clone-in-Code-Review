		addListener(SWT.Dispose, e -> {
			if (dragImage != null) {
				dragImage.dispose();
				dragImage = null;
			}
		});
