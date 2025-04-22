		if (null != property)
			switch (property) {
			case "font-family":
				updateCSSPropertyFontFamily(fontProperties, value);
				break;
			case "font-size":
				updateCSSPropertyFontSize(fontProperties, value);
				break;
			case "font-style":
				updateCSSPropertyFontStyle(fontProperties, value);
				break;
			case "font-weight":
				updateCSSPropertyFontWeight(fontProperties, value);
				break;
			case "font":
				updateCSSPropertyFontComposite(fontProperties, value);
				break;
			default:
				break;
			}
