		Object object = wrappedPart.getObject();
		IEclipseContext context = wrappedPart.getContext();
		if (object != null && context != null) {
			ContextInjectionFactory.invoke(object, Focus.class, context);
			if (Policy.DEBUG_FOCUS) {
				Activator.trace(Policy.DEBUG_FOCUS_FLAG, "Focused: " + object, null); //$NON-NLS-1$
			}
		} else {
			if (Policy.DEBUG_FOCUS) {
				Activator.trace(Policy.DEBUG_FOCUS_FLAG,
						"Focus not set, object or context missing: " + object + ", " + context, //$NON-NLS-1$ //$NON-NLS-2$
						new IllegalStateException());
			}
		}
