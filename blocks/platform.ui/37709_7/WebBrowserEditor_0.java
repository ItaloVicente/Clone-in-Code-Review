		webBrowser.getBrowser().addProgressListener(new ProgressListener() {
			
			public void completed(ProgressEvent event) {
				webBrowser.getBrowser().removeProgressListener(this);
				String url = webBrowser.getURL();
				if (url != null && url.startsWith("http")) { //$NON-NLS-1$
					webBrowser.refresh();
				}
			}
			
			public void changed(ProgressEvent event) {
			}
		});

