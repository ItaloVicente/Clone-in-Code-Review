			tableViewer.refresh();

			if (checkedBrowser != null)
				tableViewer.setChecked(checkedBrowser, true);
		}));

		tableViewer.addCheckStateListener(e -> {
			checkNewDefaultBrowser(e.getElement());
			checkedBrowser = (IBrowserDescriptor) e.getElement();
