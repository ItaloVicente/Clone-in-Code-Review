			throw new WorkbenchException(NLS.bind(
					CommonNavigatorMessages.Attribute_Missing_Warning,
					new Object[] {
							TAG_ENABLEMENT,
							id,
							configElement.getDeclaringExtension()
									.getNamespaceIdentifier() }));
