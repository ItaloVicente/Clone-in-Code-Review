			} else if(children.length > 1){
				throw new WorkbenchException(NLS.bind(
						CommonNavigatorMessages.Attribute_Missing_Warning,
						new Object[] {
								TAG_POSSIBLE_CHILDREN,
								id,
								configElement.getDeclaringExtension()
										.getNamespaceIdentifier() }));
