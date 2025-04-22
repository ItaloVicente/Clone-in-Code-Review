		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell(display);
				shell.setLayout(new GridLayout());

				Person[] persons = new Person[] { new Person("Me"),
						new Person("Myself"), new Person("I") };

				ListViewer viewer = new ListViewer(shell);
				viewer.setContentProvider(new ArrayContentProvider());
				viewer.setInput(persons);

				Text name = new Text(shell, SWT.BORDER | SWT.READ_ONLY);

				IObservableValue selection = ViewersObservables
						.observeSingleSelection(viewer);

				IObservableValue detailObservable = BeanProperties.value((Class) selection.getValueType(), "name",
						String.class)
						.observeDetail(selection);

				new DataBindingContext().bindValue(WidgetProperties.text(SWT.NONE).observe(name), detailObservable,
						new UpdateValueStrategy(false,
								UpdateValueStrategy.POLICY_NEVER), null);

				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
