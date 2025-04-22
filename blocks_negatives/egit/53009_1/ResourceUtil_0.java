			if (changedResources != null) {
				for (IResource changedResource : changedResources) {
					if (changedResource instanceof IFile
							&& changedResource.exists()) {
						try {
							ResourceUtil.saveLocalHistory(changedResource);
						} catch (CoreException e) {
							Activator
									.logError(
											MessageFormat
													.format(CoreText.ResourceUtil_SaveLocalHistoryFailed,
															changedResource), e);
						}
