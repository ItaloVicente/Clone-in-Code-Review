				.getClassLoader(), new Class<?>[] { MPlaceholder.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object arg0, Method method,
							Object[] arg2) throws Throwable {
						if ("getRef".equals(method.getName())) {
							return part;
						}
						return null;
