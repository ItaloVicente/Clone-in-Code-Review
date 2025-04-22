		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);
			shell.setLayout(new FillLayout());

			final UI ui = new UI(shell);
			final Data data = new Data();

			DataBindingContext bindingContext = new DataBindingContext();
			bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.Modify).observe(ui.firstName),
					data.firstName);
			bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.Modify).observe(ui.lastName),
					data.lastName);

			FormattedName formattedName = new FormattedName(data.firstName, data.lastName);
