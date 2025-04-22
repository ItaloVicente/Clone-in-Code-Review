						if (RepositoryUtil.isDetachedHead(repo)) {
							setMessage(
									UIText.ResetTargetSelectionDialog_DetachedHeadState,
									IMessageProvider.INFORMATION);
						} else {
							setMessage(""); //$NON-NLS-1$
						}
