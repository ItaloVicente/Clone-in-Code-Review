			});

			GridLayoutFactory.fillDefaults().generateLayout(shell);
			shell.setSize(400, 300);
			shell.open();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
