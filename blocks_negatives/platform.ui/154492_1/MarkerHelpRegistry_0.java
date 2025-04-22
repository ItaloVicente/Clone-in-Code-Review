						try {
							IMarkerHelpContextProvider provider = (IMarkerHelpContextProvider) element
									.createExecutableExtension(ATT_PROVIDER);
							String res;
							if (provider.hasHelpContextForMarker(marker)
									&& (res = provider.getHelpContextForMarker(marker)) != null)
								return res;
						} catch (CoreException e) {
							Policy.handle(e);
