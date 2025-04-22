		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			ViewModel viewModel = new ViewModel();
			Shell shell = new View(viewModel).createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}

			System.out.println(viewModel.getText());
		});
