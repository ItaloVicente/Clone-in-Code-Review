				throw new WorkbenchException(NLS.bind(
						CommonNavigatorMessages.Attribute_Missing_Warning,
						new Object[] {
								TAG_TRIGGER_POINTS,
								id,
								configElement.getDeclaringExtension()
										.getNamespaceIdentifier() }));
