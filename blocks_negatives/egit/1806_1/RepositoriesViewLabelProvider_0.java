		String label = getSimpleText(node);
		if (label == null)
			return new StyledString(element.toString());

		StyledString text = new StyledString(label);

