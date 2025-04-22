			for (String extpt : extpts) {
				for (IConfigurationElement ce : registry.getConfigurationElementsFor(extpt)) {
					if ("provider".equals(ce.getName())) {
						for (IConfigurationElement ce2 : ce.getChildren()) {
							if (typeName.equals(ce2.getAttribute("class"))) {
								try {
									if (extpt
											.equals(DEPRECATED_ELEMENT_PROVIDER_EXTPOINT)) {
										System.err
										.println("Extension point "
												+ DEPRECATED_ELEMENT_PROVIDER_EXTPOINT
												+ " is deprecated; use "
												+ ELEMENT_PROVIDER_EXTPOINT);
									}
									provider = (IElementProvider) ce
											.createExecutableExtension("class");
									providerCache.put(o.getClass(), provider);
									return provider.getElement(o, engine);
								} catch (CoreException e1) {
									e1.printStackTrace();
								}
