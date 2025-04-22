			Object data = widget.getData();
			if (data == null) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			for (Class<?> clazz = data.getClass(); clazz != Object.class; sb
					.append(' ')) {
				sb.append(clazz.getName());
				clazz = clazz.getSuperclass();
			}
			return sb.toString();
