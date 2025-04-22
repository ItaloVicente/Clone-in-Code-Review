					if (URIHelper.isPlatformURI(attrURI)) {
						uri = URI.createURI(attrURI);
					} else {
						String bundleName = contributor.getName();
						String path = bundleName + '/' + attrURI;
						uri = URI.createPlatformPluginURI(path, false);
					}
