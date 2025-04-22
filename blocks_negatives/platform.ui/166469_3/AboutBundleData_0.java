
		BundleContext bundleContext = WorkbenchPlugin.getDefault().getBundleContext();
		ServiceReference<?> factoryRef = bundleContext.getServiceReference(SignedContentFactory.class.getName());
		if (factoryRef == null)
			throw new IllegalStateException();
		SignedContentFactory contentFactory = (SignedContentFactory) bundleContext.getService(factoryRef);
		try {
			SignedContent signedContent = contentFactory.getSignedContent(bundle);
			isSigned = signedContent != null && signedContent.isSigned();
			isSignedDetermined = true;
			return isSigned;
		} catch (IOException | GeneralSecurityException e) {
			throw (IllegalStateException) new IllegalStateException().initCause(e);
		} finally {
			bundleContext.ungetService(factoryRef);
		}
