	private final ListenerList<IKeyBindingInterceptor> fInterceptors = new ListenerList<>();

	public void addInterceptor(IKeyBindingInterceptor interceptor) {
		fInterceptors.add(interceptor);
	}

	public void removeInterceptor(IKeyBindingInterceptor interceptor) {
		fInterceptors.remove(interceptor);
	}

