			bindingContext.bindValue(WidgetProperties.text().observe(selectedCommitterName),
					BeansObservables
					.observeDetailValue(selection, "name", String.class));
			bindingContext.bindValue(WidgetProperties.text().observe(selectedCommitterFirstName),
					BeansObservables
					.observeDetailValue(selection, "firstName", String.class));
