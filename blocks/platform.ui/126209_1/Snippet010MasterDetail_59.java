		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);
			shell.setLayout(new GridLayout());

			Person[] persons = new Person[] { new Person("Me"), new Person("Myself"), new Person("I") };

			ListViewer viewer = new ListViewer(shell);
			viewer.setContentProvider(new ArrayContentProvider());
			viewer.setInput(persons);

			Text name = new Text(shell, SWT.BORDER | SWT.READ_ONLY);

			IObservableValue<Object> selection = ViewersObservables.observeSingleSelection(viewer);

			IObservableValue<String> detailObservable = BeanProperties
					.value((Class<?>) selection.getValueType(), "name", String.class).observeDetail(selection);

			new DataBindingContext().bindValue((IObservableValue<String>) WidgetProperties.text(SWT.NONE).observe(name),
					detailObservable, new UpdateValueStrategy<Object, String>(false, UpdateValueStrategy.POLICY_NEVER),
					null);

			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
