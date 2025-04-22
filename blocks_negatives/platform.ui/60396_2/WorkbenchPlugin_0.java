	/* package */ OutputStream getSplashStream() {
		ServiceReference[] ref;
		try {
			ref = bundleContext.getServiceReferences(OutputStream.class.getName(), null);
		} catch (InvalidSyntaxException e) {
			return null;
		}
		if(ref==null) {
			return null;
		}
		for (int i = 0; i < ref.length; i++) {
				Object result = bundleContext.getService(ref[i]);
				bundleContext.ungetService(ref[i]);
				return (OutputStream) result;
			}
		}
		return null;
	}

