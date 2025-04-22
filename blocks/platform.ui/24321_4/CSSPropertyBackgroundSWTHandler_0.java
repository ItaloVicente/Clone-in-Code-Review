
			Color newColor;
			if ("inherit".equals(value.getCssText())) {
				Control parentControl = getParentControl(widgetElement);
				if (parentControl == null) {
					return;
				}

				newColor = parentControl.getBackground();
			} else {
				newColor = (Color) engine.convert(value, Color.class, widget
						.getDisplay());
			}

