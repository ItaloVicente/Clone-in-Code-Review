			this.browser.addLocationListener(LocationListener.changingAdapter(event -> {
					URI uri = URI.create(event.location);
					if (!(uri.getScheme().equals("http") || uri.getScheme().equals("https")
							|| uri.getScheme().equals("file")))
						try {
							if (IUriSchemeProcessor.INSTANCE.canHandle(uri)) {
								IUriSchemeProcessor.INSTANCE.handleUri(uri);
								event.doit = false;
							}
						} catch (CoreException e) {
							WebBrowserUIPlugin.logError(e.getMessage(), e);
						}

				}));
