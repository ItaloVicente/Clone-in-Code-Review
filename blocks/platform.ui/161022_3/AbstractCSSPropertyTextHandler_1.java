			return textToInsert;
		case "uppercase":
			if (textToInsert != null) {
				return textToInsert.toUpperCase();
			}
			return textToInsert;
		case "lowercase":
			if (textToInsert != null) {
				return textToInsert.toLowerCase();
			}
			return textToInsert;
		case "inherit":
		default:
