	/**
	 *
	 */
	private void registerSelectors() {
		try {
			if (sel_toolbarButtonClicked_ == 0) {
				setupDelegateClass();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	@SuppressWarnings("restriction")
	private void setupDelegateClass() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		byte[] types = { '*', '\0' };
		int size = C.PTR_SIZEOF, align = C.PTR_SIZEOF == 4 ? 2 : 3;

		Class<?> clazz = CocoaUIHandler.class;

		proc3Args = new Callback(clazz, "actionProc", 3); //$NON-NLS-1$
		Method getAddress = Callback.class.getMethod("getAddress", new Class[0]); //$NON-NLS-1$
		Object object = getAddress.invoke(proc3Args);
		long proc3 = convertToLong(object);
		if (proc3 == 0)
			SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);

		Object fieldObj = field.get(OS.class);
		object = invokeMethod(OS.class, "objc_allocateClassPair", //$NON-NLS-1$
				new Object[] { fieldObj, "SWTCocoaEnhancerDelegate", wrapPointer(0) }); //$NON-NLS-1$
		long cls = convertToLong(object);

		invokeMethod(OS.class, "class_addIvar", new Object[] { //$NON-NLS-1$
				wrapPointer(cls), SWT_OBJECT, wrapPointer(size), Byte.valueOf((byte) align), types });

		invokeMethod(OS.class, "class_addMethod", //$NON-NLS-1$
				new Object[] { wrapPointer(cls), wrapPointer(sel_toolbarButtonClicked_), wrapPointer(proc3), "@:@" }); //$NON-NLS-1$
		invokeMethod(OS.class, "objc_registerClassPair", //$NON-NLS-1$
				new Object[] { wrapPointer(cls) });
	}

	@SuppressWarnings("restriction")
	private long registerName(String name) throws IllegalArgumentException, SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class<OS> clazz = OS.class;
		Object object = invokeMethod(clazz, "sel_registerName", new Object[] { name }); //$NON-NLS-1$
		return convertToLong(object);
	}

