				try {
					if (URIHelper.isPlatformURI(attrURI)) {
						uri = URI.createURI(attrURI);
					} else {
						String bundleName = contributor.getName();
						String path = bundleName + '/' + attrURI;
						uri = URI.createPlatformPluginURI(path, false);
					}
				} catch (RuntimeException e) {
					logger.warn(e, "Model extension has invalid location"); //$NON-NLS-1$
					continue;
				}
