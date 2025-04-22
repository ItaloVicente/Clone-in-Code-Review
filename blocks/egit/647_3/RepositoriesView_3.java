		};


		copyAction = new Action("") { //$NON-NLS-1$

			@Override
			public void run() {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				if (sel.size() == 1) {
					RepositoryTreeNode node = (RepositoryTreeNode) sel
							.getFirstElement();
					String dir = null;
					if (node.getType() == RepositoryTreeNodeType.REPO) {
						dir = node.getRepository().getDirectory().getPath();
					} else if (node.getType() == RepositoryTreeNodeType.FILE
							|| node.getType() == RepositoryTreeNodeType.FOLDER) {
						dir = ((File) node.getObject()).getPath();
					} else if (node.getType() == RepositoryTreeNodeType.WORKINGDIR) {
						dir = node.getRepository().getWorkDir().getPath();
					}
					if (dir != null) {
						Clipboard clip = null;
						try {
							clip = new Clipboard(getSite().getShell()
									.getDisplay());
							clip
									.setContents(new Object[] { dir },
											new Transfer[] { TextTransfer
													.getInstance() });
						} finally {
							if (clip != null)
								clip.dispose();
						}
					}
				}
			}

		};
		copyAction.setEnabled(false);

		getViewSite().getActionBars().setGlobalActionHandler(
				ActionFactory.COPY.getId(), copyAction);

		pasteAction = new Action("") { //$NON-NLS-1$

			@Override
			public void run() {
				String errorMessage = null;

				Clipboard clip = null;
				try {
					clip = new Clipboard(getSite().getShell().getDisplay());
					String content = (String) clip.getContents(TextTransfer
							.getInstance());
					if (content == null) {
						errorMessage = UIText.RepositoriesView_NothingToPasteMessage;
						return;
					}

					File file = new File(content);
					if (!file.exists() || !file.isDirectory()) {
						errorMessage = UIText.RepositoriesView_ClipboardContentNotDirectoryMessage;
						return;
					}

					if (!RepositoryCache.FileKey.isGitRepository(file)) {
						errorMessage = NLS
								.bind(
										UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
										content);
						return;
					}

					if (addDir(file))
						scheduleRefresh();
					else
						errorMessage = NLS.bind(
								UIText.RepositoriesView_PasteRepoAlreadyThere,
								content);
				} finally {
					if (clip != null)
						clip.dispose();
					if (errorMessage != null)
						MessageDialog.openWarning(getSite().getShell(),
								UIText.RepositoriesView_PasteFailureTitle,
								errorMessage);
				}
			}
