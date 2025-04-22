			} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				Gradient grad = (Gradient) engine.convert(value, Gradient.class, form.getDisplay());
				if (grad == null) {
					return;
				}
				List<CSSPrimitiveValue> values = grad.getValues();
				List<Color> colors = new ArrayList<>(values.size());
				for (CSSPrimitiveValue cssValue : values) {
					if (cssValue != null && cssValue.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
						Color color = (Color) engine.convert(cssValue, Color.class, form.getDisplay());
						colors.add(color);
