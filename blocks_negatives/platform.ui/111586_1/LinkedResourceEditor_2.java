					report
							.add(NLS
									.bind(
											IDEWorkbenchMessages.LinkedResourceEditor_changedTo,
											new Object[] {
													res
															.getProjectRelativePath()
															.toPortableString(),
													location.toOSString(),
													newLocation
															.toOSString() }));
