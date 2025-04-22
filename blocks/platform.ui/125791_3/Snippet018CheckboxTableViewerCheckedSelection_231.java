		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				ViewModel viewModel = createSampleModel();

				Shell shell = new View(viewModel).createShell();
				shell.open();
				while (!shell.isDisposed())
					if (!display.readAndDispatch())
						display.sleep();
			}
