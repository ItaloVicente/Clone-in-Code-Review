		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			ViewModel viewModel = createSampleModel();

			Shell shell = new View(viewModel).createShell();
			shell.open();
			while (!shell.isDisposed())
				if (!display.readAndDispatch())
					display.sleep();
