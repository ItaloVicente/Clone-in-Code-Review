						String helpContextProvider = element.getAttribute(ATT_PROVIDER);
						if (helpContextProvider == null) {
							return element.getAttribute(ATT_HELP);
						}
						try {
							IMarkerHelpContextProvider provider = (IMarkerHelpContextProvider) element
									.createExecutableExtension(ATT_PROVIDER);
							String res;
							if (provider.hasHelpContextForMarker(marker)
									&& (res = provider.getHelpContextForMarker(marker)) != null)
								return res;
						} catch (CoreException e) {
							Policy.handle(e);
						}

