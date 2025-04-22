					File parent = targetTest.getParentFile();
					while (parent != null) {
						if (new File(parent, ".project").exists()) { //$NON-NLS-1$
							setErrorMessage(NLS
									.bind(UIText.ExistingOrNewPage_NestedProjectErrorMessage,
											new String[] { prj.getName(),
													targetTest.getPath(),
													parent.getPath() }));
							break;
						}
						parent = parent.getParentFile();
					}
					if (getErrorMessage() != null)
						break;
