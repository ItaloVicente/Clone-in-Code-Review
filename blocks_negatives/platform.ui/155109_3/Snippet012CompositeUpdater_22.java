		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);

			final WritableList<Counter> list = new WritableList<>();

			Button button = new Button(shell, SWT.PUSH);
			button.setText("add");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					list.add(0, new Counter());
				}
			});

			final Composite composite = new Composite(shell, SWT.None);

			new CompositeUpdater<Counter>(composite, list) {
				@Override
				protected Widget createWidget(int index) {
					Label label = new Label(composite, SWT.BORDER);
					return label;
				}
