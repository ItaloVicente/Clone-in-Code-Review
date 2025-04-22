		context.set(IStylingEngine.class, (IStylingEngine) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class<?>[] { IStylingEngine.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						stylingEngineExecutedMethods.put(method.getName(), args);
						return null;
					}
				}));
