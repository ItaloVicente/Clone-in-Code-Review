		if (filterString != null
				&& !filterString.equals(initialText)
				&& filterString.indexOf('*') != 0
				&& filterString.indexOf('?') != 0
				&& filterString.indexOf('.') != 0) {
			filterString = '*' + filterString;
