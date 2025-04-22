		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell(display);
				shell.setLayout(new FillLayout());

				final UI ui = new UI(shell);
				final Data data = new Data();

				DataBindingContext dbc = new DataBindingContext();
				dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(ui.firstName), data.firstName);
				dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(ui.lastName), data.lastName);

				FormattedName formattedName = new FormattedName(data.firstName,
						data.lastName);

				dbc.bindValue(WidgetProperties.text(SWT.None).observe(ui.formattedName), formattedName,
						new UpdateValueStrategy(false, UpdateValueStrategy.POLICY_NEVER), null);

				shell.pack();
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
