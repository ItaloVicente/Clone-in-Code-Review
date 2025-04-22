			bindingContext.bindValue((IObservableValue<String>) WidgetProperties.text(SWT.None).observe(ui.formattedName),
					formattedName, new UpdateValueStrategy<String, String>(false, UpdateValueStrategy.POLICY_NEVER),
					null);

			shell.pack();
			shell.open();
