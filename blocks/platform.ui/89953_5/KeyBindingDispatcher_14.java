	private final ListenerList<IKeyBindingDispatcherInterceptor> fInterceptors = new ListenerList<>();

	public void addInterceptor(IKeyBindingDispatcherInterceptor interceptor) {
		fInterceptors.add(interceptor);
	}

	public void removeInterceptor(IKeyBindingDispatcherInterceptor interceptor) {
		fInterceptors.remove(interceptor);
	}

