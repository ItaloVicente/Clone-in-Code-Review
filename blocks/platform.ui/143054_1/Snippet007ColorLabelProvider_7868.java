		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);
			shell.setText("Gender Bender");
			shell.setLayout(new GridLayout());

			Table table = new Table(shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			table.setLayoutData(gridData);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText("No");
			column.setWidth(20);
			column = new TableColumn(table, SWT.NONE);
			column.setText("Name");
			column.setWidth(100);
			final TableViewer viewer = new TableViewer(table);

			IObservableList<Person> observableList = Observables.staticObservableList(persons);
			ObservableListContentProvider<Person> contentProvider = new ObservableListContentProvider<>();

			viewer.setContentProvider(contentProvider);

			IObservableMap<Person, ?>[] attributes = new IObservableMap[2];
			attributes[0] = BeanProperties.value(Person.class, "name")
					.observeDetail(contentProvider.getKnownElements());
			attributes[1] = BeanProperties.value(Person.class, "gender")
					.observeDetail(contentProvider.getKnownElements());

			class ColorLabelProvider extends ObservableMapLabelProvider implements ITableColorProvider {
				Color male = display.getSystemColor(SWT.COLOR_BLUE);

				Color female = new Color(display, 255, 192, 203);

				ColorLabelProvider(IObservableMap<?, ?>[] attributes) {
					super(attributes);
				}
