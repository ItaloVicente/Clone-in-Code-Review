		if (attr.equals("style")) {
			return swtStyles;
		} else if (attr.equals("class")) {
			String result = getCSSClass(widget);
			return result != null ? result : "";
		} else if ("swt-data-class".equals(attr)) {
			Object data = widget.getData();
			if (data == null) {
				return "";
