			Color newColor = (Color) engine.convert(value, Color.class,
					control.getDisplay());
			((ICTabRendering) renderer).setSelectedTabFill(newColor);
		} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
			Gradient grad = (Gradient) engine.convert(value, Gradient.class,
					control.getDisplay());
			Color[] colors = CSSSWTColorHelper.getSWTColors(grad,
					folder.getDisplay(), engine);
			int[] percents = CSSSWTColorHelper.getPercents(grad);
			((ICTabRendering) renderer).setSelectedTabFill(colors, percents);
