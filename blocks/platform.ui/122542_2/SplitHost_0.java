		Object object = ap.getObject();
		if (object != null && ctrl != null && !ctrl.isDisposed()) {
			ContextInjectionFactory.invoke(object, Focus.class, ap.getContext(), null);
		} else {
			if (Policy.DEBUG_FOCUS) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_FOCUS_FLAG,
						"Focus not set, object is null or widget is disposed: " + object, new IllegalStateException()); //$NON-NLS-1$
			}
