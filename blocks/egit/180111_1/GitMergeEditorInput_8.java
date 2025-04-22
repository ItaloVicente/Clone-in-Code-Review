					if (useOursFilter) {
						if (style == null) {
							style = repository.getConfig().getEnum(
									ConfigConstants.CONFIG_MERGE_SECTION, null,
									ConfigConstants.CONFIG_KEY_CONFLICTSTYLE,
									ConflictStyle.MERGE);
						}
						boolean useDiff3Style = ConflictStyle.DIFF3
								.equals(style);
						String mode = (useDiff3Style ? 'O' : 'o')
								+ Integer.toString(conflictMarkerSize);
						URI uri = EgitFileSystem.createURI(repository, gitPath,
								"WORKTREE:" + mode); //$NON-NLS-1$
						Charset rscEncoding = null;
						if (file != null) {
							String encodingName = CompareCoreUtils
									.getResourceEncoding(file);
							try {
								rscEncoding = Charset.forName(encodingName);
							} catch (IllegalArgumentException e) {
							}
						}
