					if (left instanceof LocalResourceTypedElement) {
						((LocalResourceTypedElement) left)
								.setSharedDocumentListener(
										new LocalResourceSaver(
												(LocalResourceTypedElement) left));
					}
