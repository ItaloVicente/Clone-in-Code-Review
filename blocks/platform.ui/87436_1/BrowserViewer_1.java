		browser.addVisibilityWindowListener(new VisibilityWindowAdapter() {
			@Override
			public void show(WindowEvent e) {
				Browser browser2 = (Browser) e.widget;
				if (browser2.getParent().getParent() instanceof Shell) {
					Shell shell = (Shell) browser2.getParent().getParent();
					if (e.location != null)
						shell.setLocation(e.location);
					if (e.size != null)
						shell.setSize(shell.computeSize(e.size.x, e.size.y));
					shell.open();
