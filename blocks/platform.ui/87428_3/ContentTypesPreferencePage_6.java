					.addSelectionChangedListener(event -> {
						IContentType contentType = (IContentType) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						fileAssociationViewer.setInput(contentType);

						if (contentType != null) {
							String charset = contentType
									.getDefaultCharset();
							if (charset == null) {
								charset = ""; //$NON-NLS-1$
