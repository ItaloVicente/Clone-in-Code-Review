		switch(attr){
		case "style":
			return () -> swtStyles;
		case "class":
			return () -> Objects.toString(getCSSClass(widget), "");
		case "swt-data-class":
			return () -> {
				Object data = widget.getData();
				if (data == null) {
					return "";
				}
				StringBuilder sb = new StringBuilder();
				for (Class<?> clazz = data.getClass(); clazz != Object.class; sb.append(' ')) {
					sb.append(clazz.getName());
					clazz = clazz.getSuperclass();
				}
				return sb.toString();
			};
		default:
			Object o = widget.getData(attr.toLowerCase());
			if (o != null) {
				return () -> o.toString();
