								uri, tw.getNameString(), file, rscEncoding);
						if (file != null) {
							item.setSharedDocumentListener(
									new LocalResourceSaver(item) {

										@Override
										protected void save()
												throws CoreException {
											super.save();
											file.refreshLocal(
													IResource.DEPTH_ZERO, null);
										}
									});
						} else {
							item.setSharedDocumentListener(
									new LocalResourceSaver(item));
						}
					} else {
						if (file != null) {
							item = new LocalResourceTypedElement(file);
						} else {
							item = createWithHiddenResource(
									location.toFile().toURI(),
									tw.getNameString(), null, null);
						}
						item.setSharedDocumentListener(
								new LocalResourceSaver(item));
