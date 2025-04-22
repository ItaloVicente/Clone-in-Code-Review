	static IOperatingSystemRegistration getInstance() throws CoreException {
		IExtensionRegistry registry = RegistryFactory.getRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor("org.eclipse.urischeme.uriSchemeOperatingSystemRegistrations"); //$NON-NLS-1$

		IConfigurationElement element = Arrays.stream(elements)
				.filter(e -> Platform.getOS().equals(e.getAttribute("os"))).findFirst().orElse(null); //$NON-NLS-1$
		if (element == null) {
			return null;
		}

		Object executableExtension = element.createExecutableExtension("class"); //$NON-NLS-1$
		if (!(executableExtension instanceof IOperatingSystemRegistration)) {
			throw new CoreException(new Status(IStatus.ERROR, IOperatingSystemRegistration.class,
					"Registered class has wrong type: " + executableExtension.getClass())); //$NON-NLS-1$
