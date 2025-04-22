			StringBuilder sb = new StringBuilder();
			for (Class<?> clazz = data.getClass(); clazz != Object.class; sb
					.append(' ')) {
				sb.append(clazz.getName());
				clazz = clazz.getSuperclass();
			}
			return sb.toString();
		}
		Object o = widget.getData(attr.toLowerCase());
		if (o != null) {
			return o.toString();
