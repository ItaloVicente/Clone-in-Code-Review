			this.browser.addLocationListener(LocationListener.changingAdapter(event -> {
					URI uri = URI.create(event.location);
					if (!(uri.getScheme().equals("http") || uri.getScheme().equals("https")
							|| uri.getScheme().equals("file")))
						try {
							IUriSchemeProcessor.INSTANCE.handleUri(uri.getScheme(), uri.toString());
							event.doit = false;
						} catch (CoreException e) {
							WebBrowserUIPlugin.logError(e.getMessage(), e);
						}

				}));
