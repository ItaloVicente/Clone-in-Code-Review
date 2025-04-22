
		bindingContext.bindValue(radioGroup, color);

		bindingContext.bindValue(listSelection, color,
				UpdateValueStrategy.create(EnumConverters.fromString(Color.class)),
				UpdateValueStrategy.create(EnumConverters.toString(Color.class)));
