
		showLocationInWindowTitle.addListener(SWT.Selection, e -> {
			if (showLocationFullPath != null && showLocationInWindowTitle != null)
				showLocationFullPath
						.setEnabled(showLocationInWindowTitle.isEnabled() && showLocationInWindowTitle.getSelection());
		});
