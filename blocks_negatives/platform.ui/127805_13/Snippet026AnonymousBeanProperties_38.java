		dbc.bindValue(WidgetProperties.enabled().observe(
				statusViewer.getControl()), new ComputedValue() {
			@Override
			protected Object calculate() {
				return Boolean.valueOf(selection.getValue() instanceof Contact);
			}
		});
