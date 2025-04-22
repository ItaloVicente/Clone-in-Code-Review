			Class selectedPersonValueType = null;
			if (selectedPerson.getValueType() instanceof Class<?>) {
				selectedPersonValueType = (Class) selectedPerson.getValueType();
			}
			dbc.bindValue(
					WidgetProperties.text(SWT.Modify).observe(name),
					BeanProperties.value(selectedPersonValueType, "name", String.class)
					.observeDetail(selectedPerson));
