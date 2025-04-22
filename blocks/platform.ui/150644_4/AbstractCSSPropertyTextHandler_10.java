
		String textTransform = ((CSSPrimitiveValue) value).getStringValue();
		switch (textTransform) {
		case "capitalize":
		case "uppercase":
		case "lowercase":
			return true;
		}

