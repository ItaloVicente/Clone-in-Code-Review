					if (useOursFilter) {
						if (style == null) {
							style = repository.getConfig().getEnum(
									ConfigConstants.CONFIG_MERGE_SECTION, null,
									ConfigConstants.CONFIG_KEY_CONFLICTSTYLE,
									ConflictStyle.MERGE);
						}
						boolean useDiff3Style = ConflictStyle.DIFF3
								.equals(style);
						String filter = (useDiff3Style ? 'O' : 'o')
								+ Integer.toString(conflictMarkerSize);
						URI uri = EgitFileSystem.createURI(repository, gitPath,
								"WORKTREE:" + filter); //$NON-NLS-1$
						Charset rscEncoding = null;
						if (file != null) {
							if (encoding == null) {
								encoding = CompareCoreUtils
										.getResourceEncoding(file);
							}
							try {
								rscEncoding = Charset.forName(encoding);
							} catch (IllegalArgumentException e) {
							}
						}
						item = createWithHiddenResource(
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
