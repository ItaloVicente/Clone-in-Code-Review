						String name = dialog.getName();
						String extension = dialog.getExtension();
						try {
							if (spec.name != null) {
								selectedContentType.removeFileSpec(spec.name,
										IContentType.FILE_NAME_SPEC);
							} else if (spec.ext != null) {
								selectedContentType.removeFileSpec(spec.ext,
										IContentType.FILE_EXTENSION_SPEC);
							}
