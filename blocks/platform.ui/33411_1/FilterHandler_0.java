			if (Util.isCocoa()
					&& methodName.startsWith("objc_msgSend")
					&& className.equals("org.eclipse.swt.internal.cocoa.OS")
					&& stackTraces.length > 1) {
				element = stackTraces[1];
				methodName = element.getMethodName();
				className = element.getClassName();
			}
