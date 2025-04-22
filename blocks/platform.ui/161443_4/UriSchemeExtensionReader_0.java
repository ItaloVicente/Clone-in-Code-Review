
	private static IStatus checkNewlyAvailableUriSchemeHandlers() {
		IOperatingSystemRegistration osRegistration = IOperatingSystemRegistration.getInstance();
		IUriSchemeExtensionReader extensionReader = IUriSchemeExtensionReader.newInstance();
		IEclipsePreferences preferenceNode = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
		Collection<String> processedSchemes = new LinkedHashSet<>(Arrays
				.asList(preferenceNode.get(PROCESSED_SCHEMES_PREFERENCE, "").split(SCHEME_LIST_PREFERENCE_SEPARATOR))); //$NON-NLS-1$
		Collection<IScheme> toProcessSchemes = new LinkedHashSet<>(extensionReader.getSchemes());
		toProcessSchemes.removeIf(scheme -> processedSchemes.contains(scheme.getName()));
		try {
			Set<String> alreadyHandlerSchemes = osRegistration.getSchemesInformation(toProcessSchemes).stream() //
					.filter(ISchemeInformation::isHandled) //
					.map(ISchemeInformation::getName) //
					.collect(Collectors.toSet());
			toProcessSchemes.removeIf(scheme -> alreadyHandlerSchemes.contains(scheme.getName()));
			osRegistration.handleSchemes(toProcessSchemes, Collections.emptyList());
			processedSchemes.addAll(toProcessSchemes.stream().map(IScheme::getName).collect(Collectors.toList()));
			preferenceNode.put(PROCESSED_SCHEMES_PREFERENCE,
					processedSchemes.stream().collect(Collectors.joining(SCHEME_LIST_PREFERENCE_SEPARATOR)));
			preferenceNode.flush();
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
		}
	}

