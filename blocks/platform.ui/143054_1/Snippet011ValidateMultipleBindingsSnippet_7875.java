		dbc.bindValue(
				WidgetProperties.text(SWT.Modify).observe(view.text1), model.value1,
				new UpdateValueStrategy<String, String>()
						.setAfterConvertValidator(new CrossFieldValidator(model.value2)), null);

		dbc.bindValue(
				WidgetProperties.text(SWT.Modify).observe(view.text2), model.value2,
				new UpdateValueStrategy<String, String>()
					.setAfterConvertValidator(new CrossFieldValidator(model.value1)), null);
