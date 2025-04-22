						if (myUri.equals(uri) || myUris.contains(uri)) {
							String message = NLS
									.bind(
											UIText.ConfigureUriPage_DuplicateUriMessage,
											uri.toPrivateString());
							MessageDialog.openInformation(getShell(),
									UIText.ConfigureUriPage_DuplicateUriTitle,
									message);
							return;
						}
						myUris.add(uri);
