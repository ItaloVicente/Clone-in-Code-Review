		for (int i = 0; i < methods.length; i++) {
			String method = methods[i];
			int lastDot = method.lastIndexOf('.');
			filterMethods[i] = lastDot >= 0 ?
					new StackFrame(method.substring(0, lastDot), method.substring(lastDot + 1)) :
					new StackFrame("", method); //$NON-NLS-1$
