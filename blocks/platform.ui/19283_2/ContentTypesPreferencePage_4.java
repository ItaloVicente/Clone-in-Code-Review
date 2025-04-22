						IFileTypeProcessor processor = new FileTypeProcessor();
						String pattern = dialog.getPattern();
						if (processor.isValidFileType(pattern)) {
							String name = processor.getName(pattern);
							String extension = processor.getExtension(pattern);
							try {
								if (spec.name != null) {
									selectedContentType.removeFileSpec(spec.name,
											IContentType.FILE_NAME_SPEC);
								} else if (spec.ext != null) {
									selectedContentType.removeFileSpec(spec.ext,
											IContentType.FILE_EXTENSION_SPEC);
								}
