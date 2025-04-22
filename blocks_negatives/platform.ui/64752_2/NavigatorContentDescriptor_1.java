			throw new WorkbenchException(NLS.bind(
					CommonNavigatorMessages.Attribute_Missing_Warning,
					new Object[] {
							ATT_ID,
							id,
							configElement.getDeclaringExtension()
									.getNamespaceIdentifier() }));
