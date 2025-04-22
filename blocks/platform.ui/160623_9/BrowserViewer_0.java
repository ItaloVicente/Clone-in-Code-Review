			this.browser.addLocationListener(LocationListener.changingAdapter(event -> {
					URI uri = URI.create(event.location);
					if (!(uri.getScheme().equals("http") || uri.getScheme().equals("https") //$NON-NLS-1$ //$NON-NLS-2$
							|| uri.getScheme().equals("file"))) //$NON-NLS-1$
						try {
							if (IUriSchemeProcessor.INSTANCE.canHandle(uri)) {
								IUriSchemeProcessor.INSTANCE.handleUri(uri);
								event.doit = false;
							}
						} catch (CoreException e) {
							WebBrowserUIPlugin.logError(e.getMessage(), e);
						}

				}));
