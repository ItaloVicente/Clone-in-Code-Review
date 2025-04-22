		@Override
		public void modifiedBundle(Bundle bundle, BundleEvent event, FragmentWrapperElementMapping mapping) {
		}

		@Override
		public void removedBundle(Bundle bundle, BundleEvent event, FragmentWrapperElementMapping mapping) {
			uiSync.asyncExec(() -> {
				if (mapping != null) {
					for (MApplicationElement appElement : mapping.elements) {
						if (appElement instanceof MUIElement) {
							MUIElement element = (MUIElement) appElement;
							element.setToBeRendered(false);
							if (element.getParent() != null) {
								element.getParent().getChildren().remove(element);
							}
						}
					}

					String bundleName = bundle.getSymbolicName();
					String fragmentHeader = bundle.getHeaders("").get(MODEL_FRAGMENT_HEADER); //$NON-NLS-1$
					String[] fr = fragmentHeader.split(";"); //$NON-NLS-1$
					if (fr.length > 0) {
						String attrURI = fr[0];
						E4XMIResource applicationResource = (E4XMIResource) ((EObject) application).eResource();
						ResourceSet resourceSet = applicationResource.getResourceSet();
						if (attrURI == null) {
							log(LogLevel.WARN, "Unable to find location for the model extension \"{0}\"", bundleName); //$NON-NLS-1$
							return;
						}

						URI uri;
						try {
							if (URIHelper.isPlatformURI(attrURI)) {
								uri = URI.createURI(attrURI);
							} else {
								String path = bundleName + '/' + attrURI;
								uri = URI.createPlatformPluginURI(path, false);
							}
						} catch (RuntimeException e) {
							log(LogLevel.WARN, "Invalid location \"{0}\" of model extension \"\"", attrURI, bundleName, //$NON-NLS-1$
									e);
							return;
						}

						try {
							Resource resource = resourceSet.getResource(uri, true);
							resource.unload();
						} catch (RuntimeException e) {
							log(LogLevel.WARN, "Unable to read model extension from \"{0}\" of \"{1}\"", uri, //$NON-NLS-1$
									bundleName);
						}
					}

				}
			});
		}
	}

	private MApplication application;
	private IEclipseContext context;
	private UISynchronize uiSync;
	private boolean initial;
