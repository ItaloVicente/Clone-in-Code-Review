				.getClassLoader(), new Class<?>[] { MPlaceholder.class }, (Object arg0, Method method, Object[] arg2) -> {
				    if ("getRef".equals(method.getName())) {
					return part;
				    }
				    return null;
		});
