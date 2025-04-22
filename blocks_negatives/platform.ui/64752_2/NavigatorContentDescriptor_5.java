			throw new WorkbenchException(NLS.bind(
					CommonNavigatorMessages.Too_many_elements_Warning,
					new Object[] {
							TAG_OVERRIDE,
							id,configElement.getDeclaringExtension()
							.getNamespaceIdentifier() }));
