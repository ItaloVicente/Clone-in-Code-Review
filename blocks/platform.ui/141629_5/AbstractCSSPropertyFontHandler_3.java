		if (null != property)
			switch (property) {
			case "font":
				applyCSSPropertyFont(element, value, pseudo, engine);
				break;
			case "font-family":
				applyCSSPropertyFontFamily(element, value, pseudo, engine);
				break;
			case "font-size":
				applyCSSPropertyFontSize(element, value, pseudo, engine);
				break;
			case "font-adjust":
				applyCSSPropertyFontSizeAdjust(element, value, pseudo, engine);
				break;
			case "font-stretch":
				applyCSSPropertyFontStretch(element, value, pseudo, engine);
				break;
			case "font-style":
				applyCSSPropertyFontStyle(element, value, pseudo, engine);
				break;
			case "font-variant":
				applyCSSPropertyFontVariant(element, value, pseudo, engine);
				break;
			case "font-weight":
				applyCSSPropertyFontWeight(element, value, pseudo, engine);
				break;
			default:
				break;
			}
