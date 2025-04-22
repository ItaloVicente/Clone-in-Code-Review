						IFileTypeProcessor processor = new FileTypeProcessor();
						String pattern = dialog.getPattern();
						if (processor.isValidFileType(pattern)) {
							String name = processor.getName(pattern);
							String extension = processor.getExtension(pattern);
							try {
								if (name.equals("*")) { //$NON-NLS-1$
									selectedContentType.addFileSpec(extension,
											IContentType.FILE_EXTENSION_SPEC);
								} else {
									selectedContentType.addFileSpec(name
											+ (extension.length() > 0 ? ('.' + extension) : ""), //$NON-NLS-1$
											IContentType.FILE_NAME_SPEC);
								}
							} catch (CoreException ex) {
								StatusUtil.handleStatus(ex.getStatus(), StatusManager.SHOW, shell);
								WorkbenchPlugin.log(ex);
							} finally {
								fileAssociationViewer.refresh(false);
