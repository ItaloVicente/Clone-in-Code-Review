			if(decorator instanceof IColorDecorator){
				IColorDecorator colorDecorator = (IColorDecorator) decorator;
				Color background = colorDecorator.decorateBackground(element);
				if (background != null)
					settings.setBackground(background);
				Color foreground = colorDecorator.decorateForeground(element);
				if (foreground != null)
					settings.setForeground(foreground);
			}

			if(decorator instanceof IFontDecorator) {
				Font font = ((IFontDecorator) decorator).decorateFont(element);
				if (font != null)
					settings.setFont(font);
			}
		}
