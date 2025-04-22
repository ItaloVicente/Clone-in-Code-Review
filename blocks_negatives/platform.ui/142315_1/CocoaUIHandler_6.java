	/*
	 * Action implementation for the toolbar button
	 */
	@SuppressWarnings("restriction")
	void toolbarButtonClicked(NSControl source) {
		try {
			NSWindow window = source.window();
			Object idValue = idField.get(window);

			Display display = Display.getCurrent();
			Widget widget = (Widget) invokeMethod(Display.class, display, "findWidget", new Object[] { idValue }); //$NON-NLS-1$

			if (!(widget instanceof Shell)) {
				return;
			}
			Shell shell = (Shell) widget;
			for (MWindow mwin : app.getChildren()) {
				if (mwin.getWidget() == shell) {
					if (!runCommand(COMMAND_ID_TOGGLE_COOLBAR)) {
						runAction(COMMAND_ID_TOGGLE_COOLBAR);
					}
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	static int actionProc(int id, int sel, int arg0) throws Exception {
		return (int) actionProc((long) id, (long) sel, (long) arg0);
	}

	@SuppressWarnings("restriction")
	static long actionProc(long id, long sel, long arg0) throws Exception {
		long[] jniRef = OS_object_getInstanceVariable(id, SWT_OBJECT);
		if (jniRef[0] == 0)
			return 0;

		CocoaUIHandler delegate = (CocoaUIHandler) invokeMethod(OS.class, "JNIGetObject", //$NON-NLS-1$
				new Object[] { wrapPointer(jniRef[0]) });

		if (sel == sel_toolbarButtonClicked_) {
			NSControl source = new_NSControl(arg0);
			delegate.toolbarButtonClicked(source);
		}

		return 0;
	}


	@SuppressWarnings("restriction")
	private static NSControl new_NSControl(long arg0) throws NoSuchMethodException, InstantiationException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<NSControl> clazz = NSControl.class;
		Class<?> PTR_CLASS = C.PTR_SIZEOF == 8 ? long.class : int.class;
		Constructor<NSControl> constructor = clazz.getConstructor(new Class[] { PTR_CLASS });
		return constructor.newInstance(new Object[] { wrapPointer(arg0) });
	}

	/**
	 * Specialized method. It's behavior is isolated and different enough from the
	 * usual invocation that custom code is warranted.
	 */
	@SuppressWarnings("restriction")
	private static long[] OS_object_getInstanceVariable(long delegateId, byte[] name) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Class<OS> clazz = OS.class;
		Method method = null;
		Class<?> PTR_CLASS = C.PTR_SIZEOF == 8 ? long.class : int.class;
		if (PTR_CLASS == long.class) {
			method = clazz.getMethod("object_getInstanceVariable", new Class[] { //$NON-NLS-1$
					long.class, byte[].class, long[].class });
			long[] resultPtr = new long[1];
			method.invoke(null, new Object[] { Long.valueOf(delegateId), name, resultPtr });
			return resultPtr;
		} else {
			method = clazz.getMethod("object_getInstanceVariable", new Class[] { //$NON-NLS-1$
					int.class, byte[].class, int[].class });
			int[] resultPtr = new int[1];
			method.invoke(null, new Object[] { Integer.valueOf((int) delegateId), name, resultPtr });
			return new long[] { resultPtr[0] };
		}
	}

	private long convertToLong(Object object) {
		if (object instanceof Integer) {
			Integer i = (Integer) object;
			return i.longValue();
		}
		if (object instanceof Long) {
			Long l = (Long) object;
			return l.longValue();
		}
		return 0;
	}

	private static Object invokeMethod(Class<?> clazz, String methodName, Object[] args)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException,
			NoSuchMethodException {
		return invokeMethod(clazz, null, methodName, args);
	}

	private static Object invokeMethod(Class<?> clazz, Object target, String methodName, Object[] args)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException,
			NoSuchMethodException {
		Class<?>[] signature = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			Class<?> thisClass = args[i].getClass();
			if (thisClass == Integer.class)
				signature[i] = int.class;
			else if (thisClass == Long.class)
				signature[i] = long.class;
			else if (thisClass == Byte.class)
				signature[i] = byte.class;
			else
				signature[i] = thisClass;
		}
		Method method = clazz.getMethod(methodName, signature);
		return method.invoke(target, args);
	}

	@SuppressWarnings("restriction")
	private static Object wrapPointer(long value) {
		Class<?> PTR_CLASS = C.PTR_SIZEOF == 8 ? long.class : int.class;
		if (PTR_CLASS == long.class)
			return Long.valueOf(value);
		else
			return Integer.valueOf((int) value);
	}

