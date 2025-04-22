		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = ((IStructuredSelection) tableViewer
						.getSelection());
				Object firstElem = sel.getFirstElement();
				if (firstElem != null && !(firstElem instanceof SystemBrowserDescriptor)) {
					IBrowserDescriptor browser2 = (IBrowserDescriptor) sel
							.getFirstElement();
					IBrowserDescriptorWorkingCopy wc = browser2
							.getWorkingCopy();
					BrowserDescriptorDialog dialog = new BrowserDescriptorDialog(
							getShell(), wc);
					if (dialog.open() != Window.CANCEL) {
						try {
							tableViewer.refresh(wc.save());
						} catch (Exception ex) {
						}
