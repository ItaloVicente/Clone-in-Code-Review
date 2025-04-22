		IValueProperty<Boolean, Font> fontProperty = BindingProperties
				.convertedValue(IConverter.create(null, null, stale -> stale ? italicFont : shellFont));

		bindingContext.bindValue(WidgetProperties.font().observe(field2), fontProperty.observeDetail(stale1));
		bindingContext.bindValue(WidgetProperties.font().observe(field1), fontProperty.observeDetail(stale2));

