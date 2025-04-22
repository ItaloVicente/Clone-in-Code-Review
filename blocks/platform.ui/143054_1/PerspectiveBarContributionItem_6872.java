		String returnText = textValue;
		GC gc = new GC(item.getParent());
		int maxWidth = getMaxWidth(item.getImage());
		if (gc.textExtent(textValue).x >= maxWidth) {
			for (int i = textValue.length(); i > 0; i--) {
				String test = textValue.substring(0, i);
				test = test + ellipsis;
				if (gc.textExtent(test).x < maxWidth) {
					returnText = test;
					break;
				}
			}
		}
		gc.dispose();
		return returnText;
	}
