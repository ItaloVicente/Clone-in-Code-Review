		remove.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			IStructuredSelection sel = ((IStructuredSelection) tableViewer.getSelection());
			IBrowserDescriptor browser2 = (IBrowserDescriptor) sel.getFirstElement();
			try {
				browser2.delete();
				tableViewer.remove(browser2);

				BrowserManager manager = BrowserManager.getInstance();
				if (browser2 == checkedBrowser) {
					if (manager.browsers.size() > 0) {
						checkedBrowser = manager.browsers.get(0);
						tableViewer.setChecked(checkedBrowser, true);
