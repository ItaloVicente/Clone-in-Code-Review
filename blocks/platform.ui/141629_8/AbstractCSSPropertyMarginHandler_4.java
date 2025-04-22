		if (null != property)
			switch (property) {
			case "margin":
				applyCSSPropertyMargin(element, value, pseudo, engine);
				break;
			case "margin-top":
				applyCSSPropertyMarginTop(element, value, pseudo, engine);
				break;
			case "margin-right":
				applyCSSPropertyMarginRight(element, value, pseudo, engine);
				break;
			case "margin-bottom":
				applyCSSPropertyMarginBottom(element, value, pseudo, engine);
				break;
			case "margin-left":
				applyCSSPropertyMarginLeft(element, value, pseudo, engine);
				break;
			default:
				break;
			}
