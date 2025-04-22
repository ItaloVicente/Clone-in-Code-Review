						String name = dialog.getName();
						String extension = dialog.getExtension();
						try {
								selectedContentType.addFileSpec(extension,
										IContentType.FILE_EXTENSION_SPEC);
							} else {
								selectedContentType
										.addFileSpec(
												name
														+ (extension.length() > 0 ? ('.' + extension)
																: ""), //$NON-NLS-1$
												IContentType.FILE_NAME_SPEC);
