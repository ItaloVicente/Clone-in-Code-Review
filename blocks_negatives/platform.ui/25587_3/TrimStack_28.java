		if (result == null || result.length() == 0)
			return null;

		if (element instanceof MUILabel) {
			String label = ((MUILabel)element).getLocalizedLabel();
			if (label != null && label.length() > 0) {
				result = label + ' ' + '(' + result + ')';
			}
		}

