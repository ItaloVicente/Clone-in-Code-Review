		final Class focusManagerClass = Class
		final Method focusManagerGetCurrentManagerMethod = focusManagerClass
		final Object focusManager = focusManagerGetCurrentManagerMethod
		        .invoke(focusManagerClass);
		final Method focusManagerGetFocusOwner = focusManagerClass
		final Object focusComponent = focusManagerGetFocusOwner
		        .invoke(focusManager);
