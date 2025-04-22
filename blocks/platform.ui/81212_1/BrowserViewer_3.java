		browser.addOpenWindowListener(event -> {
			Shell shell2 = new Shell(getShell(), SWT.SHELL_TRIM);
			shell2.setLayout(new FillLayout());
			shell2.setText(Messages.viewWebBrowserTitle);
			shell2.setImage(getShell().getImage());
			if (event.location != null)
				shell2.setLocation(event.location);
			if (event.size != null)
				shell2.setSize(event.size);
			int style = 0;
			if (showURLbar)
				style += LOCATION_BAR;
			if (showToolbar)
				style += BUTTON_BAR;
			BrowserViewer browser2 = new BrowserViewer(shell2, style);
			browser2.newWindow = true;
			event.browser = browser2.browser;
		});
