		browser.addCloseWindowListener(event -> {
			if (newWindow)
				getShell().dispose();
			else
				container.close();
		});
