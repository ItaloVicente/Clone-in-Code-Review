					.addSelectionChangedListener(new ISelectionChangedListener() {

						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							IContentType contentType = (IContentType) ((IStructuredSelection) event
									.getSelection()).getFirstElement();
							fileAssociationViewer.setInput(contentType);

							if (contentType != null) {
								String charset = contentType
										.getDefaultCharset();
								if (charset == null) {
								}
								charsetField.setText(charset);
							} else {
