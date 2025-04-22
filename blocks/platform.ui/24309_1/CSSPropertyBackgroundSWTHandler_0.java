			Color newColor;
			if ("inherit".equals(value.getCssText())) {
				newColor = null;
			} else {
				newColor = (Color) engine.convert(value, Color.class, widget
						.getDisplay());
			}
