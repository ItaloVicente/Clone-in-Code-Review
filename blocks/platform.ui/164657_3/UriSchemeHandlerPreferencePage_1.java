	IOperatingSystemRegistration getOperationgSystemRegistration() throws CoreException {
		return IOperatingSystemRegistration.getInstance();
	}

	IUriSchemeExtensionReader getUriSchemeExtensionReader() {
		return IUriSchemeExtensionReader.newInstance();
	}

