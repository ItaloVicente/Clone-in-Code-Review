				report
						.add(NLS
								.bind(
										IDEWorkbenchMessages.LinkedResourceEditor_changedTo,
										new Object[] {
												res.getProjectRelativePath()
														.toPortableString(),
												res.getRawLocation()
														.toOSString(),
												location.toOSString() }));
