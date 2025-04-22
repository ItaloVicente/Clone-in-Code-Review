		if (property != null)
			switch (property) {
			case "border-style":
				updateCSSPropertyBorderStyle(borderProperties, value);
				break;
			case "border-color":
				updateCSSPropertyBorderColor(borderProperties, value);
				break;
			case "border-width":
				updateCSSPropertyBorderWidth(borderProperties, value);
				break;
			default:
				break;
			}
