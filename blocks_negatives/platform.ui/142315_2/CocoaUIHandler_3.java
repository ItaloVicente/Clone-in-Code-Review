	/**
	 * @param display
	 */
	protected void allocateDelegate(Display display) {
		try {
			delegate = new SWTCocoaEnhancerDelegate();
			delegate.alloc().init();
			Method method = OS.class.getMethod("NewGlobalRef", new Class[] { Object.class }); //$NON-NLS-1$
			Object object = method.invoke(OS.class, new Object[] { CocoaUIHandler.this });
			delegateJniRef = convertToLong(object);
		} catch (Exception e) {
			log(e);
		}
		if (delegateJniRef == 0)
			SWT.error(SWT.ERROR_NO_HANDLES);

		try {
			Object idValue = idField.get(delegate);
			invokeMethod(OS.class, "object_setInstanceVariable", //$NON-NLS-1$
					new Object[] { idValue, SWT_OBJECT, wrapPointer(delegateJniRef) });
			display.disposeExec(new Runnable() {
				@Override
				public void run() {
					if (delegateJniRef != 0) {
						try {
							invokeMethod(OS.class, "DeleteGlobalRef", new Object[] { wrapPointer(delegateJniRef) }); //$NON-NLS-1$
						} catch (Exception e) {
							log(e);
						}
					}
					delegateJniRef = 0;

					if (delegate != null)
						delegate.release();
					delegate = null;
				}
			});
		} catch (Exception e) {
			log(e);
		}
	}

