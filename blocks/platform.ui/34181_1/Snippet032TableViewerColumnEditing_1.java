			bindingContext.bindValue(
					WidgetProperties.text().observe(selectedCommitterName),
					BeanProperties.value((Class) selection.getValueType(), "name", String.class)
					.observeDetail(selection));
			bindingContext.bindValue(
					WidgetProperties.text().observe(selectedCommitterFirstName),
					BeanProperties.value((Class) selection.getValueType(), "firstName", String.class)
					.observeDetail(selection));
