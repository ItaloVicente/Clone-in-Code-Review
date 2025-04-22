		DataBindingContext bindingContext = new DataBindingContext();
		bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.Modify).observe(ui.firstName),
				data.firstName);
		bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.Modify).observe(ui.lastName),
				data.lastName);

		FormattedName formattedName = new FormattedName(data.firstName, data.lastName);

		bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.None).observe(ui.formattedName),
				formattedName, new UpdateValueStrategy<String, String>(false, UpdateValueStrategy.POLICY_NEVER), null);

		shell.pack();
		shell.open();
		return shell;
	}
