			}
		}

		if (node.getType() == RepositoryTreeNodeType.TAG) {

			final Ref ref = (Ref) node.getObject();

			MenuItem checkout = new MenuItem(men, SWT.PUSH);
			checkout.setText(UIText.RepositoriesView_CheckOut_MenuItem);

			checkout.setEnabled(!isRefCheckedOut(node.getRepository(), ref
					.getName()));

			checkout.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					checkoutBranch(node, ref.getLeaf().getName());
				}
			});
		}

		if (node.getType() == RepositoryTreeNodeType.BRANCHES
				|| node.getType() == RepositoryTreeNodeType.LOCALBRANCHES)
			createCreateBranchItem(men, node);

		if (node.getType() == RepositoryTreeNodeType.REPO) {

			final Repository repo = (Repository) node.getObject();


			MenuItem remove = new MenuItem(men, SWT.PUSH);
			remove.setText(UIText.RepositoriesView_Remove_MenuItem);
			remove.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					removeRepository(new NullProgressMonitor(), repo);
				}
			});

			new MenuItem(men, SWT.SEPARATOR);

			MenuItem fetchItem = new MenuItem(men, SWT.PUSH);
			fetchItem.setText(UIText.RepositoriesView_FetchMenu);
			fetchItem.setImage(UIIcons.FETCH.createImage());
			fetchItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						new WizardDialog(getSite().getShell(), new FetchWizard(
								repo)).open();
					} catch (URISyntaxException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
				}

			});

			MenuItem pushItem = new MenuItem(men, SWT.PUSH);
			pushItem.setText(UIText.RepositoriesView_PushMenu);
			pushItem.setImage(UIIcons.PUSH.createImage());
			pushItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						new WizardDialog(getSite().getShell(), new PushWizard(
								repo)).open();
					} catch (URISyntaxException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
				}

			});



			new MenuItem(men, SWT.SEPARATOR);

			if (!isBare) {
				createImportProjectItem(men, repo, repo.getWorkDir().getPath());

				new MenuItem(men, SWT.SEPARATOR);
			}

			MenuItem openPropsView = new MenuItem(men, SWT.PUSH);
			openPropsView.setText(UIText.RepositoriesView_OpenPropertiesMenu);
			openPropsView.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(
										IPageLayout.ID_PROP_SHEET);
					} catch (PartInitException e1) {
					}
				}

			});

			new MenuItem(men, SWT.SEPARATOR);

			createCopyPathItem(men, repo.getDirectory().getPath());
		}

		if (node.getType() == RepositoryTreeNodeType.REMOTES) {

			MenuItem remoteConfig = new MenuItem(men, SWT.PUSH);
			remoteConfig.setText(UIText.RepositoriesView_NewRemoteMenu);
			remoteConfig.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new NewRemoteWizard(node.getRepository()));
					if (dlg.open() == Window.OK)
						tv.refresh();

				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.REMOTE) {

			final String configName = (String) node.getObject();

			RemoteConfig rconfig;
			try {
				rconfig = new RemoteConfig(node.getRepository().getConfig(),
						configName);
			} catch (URISyntaxException e2) {
				rconfig = null;
			}

			boolean fetchExists = rconfig != null
					&& !rconfig.getURIs().isEmpty();
			boolean pushExists = rconfig != null
					&& !rconfig.getPushURIs().isEmpty();

			if (!fetchExists) {
				MenuItem configureUrlFetch = new MenuItem(men, SWT.PUSH);
				configureUrlFetch
						.setText(UIText.RepositoriesView_CreateFetch_menu);

				configureUrlFetch.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {

						WizardDialog dlg = new WizardDialog(getSite()
								.getShell(), new ConfigureRemoteWizard(node
								.getRepository(), configName, false));
						if (dlg.open() == Window.OK)
							tv.refresh();

					}

				});
			}

			if (!pushExists) {
				MenuItem configureUrlPush = new MenuItem(men, SWT.PUSH);

				configureUrlPush
						.setText(UIText.RepositoriesView_CreatePush_menu);

				configureUrlPush.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {

						WizardDialog dlg = new WizardDialog(getSite()
								.getShell(), new ConfigureRemoteWizard(node
								.getRepository(), configName, true));
						if (dlg.open() == Window.OK)
							tv.refresh();

					}

				});
			}

			if (!fetchExists || !pushExists)
				new MenuItem(men, SWT.SEPARATOR);

			MenuItem removeRemote = new MenuItem(men, SWT.PUSH);
			removeRemote.setText(UIText.RepositoriesView_RemoveRemoteMenu);
			removeRemote.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					boolean ok = MessageDialog
							.openConfirm(
									getSite().getShell(),
									UIText.RepositoriesView_ConfirmDeleteRemoteHeader,
									NLS
											.bind(
													UIText.RepositoriesView_ConfirmDeleteRemoteMessage,
													configName));
					if (ok) {
						RepositoryConfig config = node.getRepository()
								.getConfig();
						config.unsetSection(REMOTE, configName);
						try {
							config.save();
							tv.refresh();
						} catch (IOException e1) {
							Activator.handleError(
									UIText.RepositoriesView_ErrorHeader, e1,
									true);
						}
					}

				}

			});

			new MenuItem(men, SWT.SEPARATOR);

			MenuItem openPropsView = new MenuItem(men, SWT.PUSH);
			openPropsView.setText(UIText.RepositoriesView_OpenPropertiesMenu);
			openPropsView.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(
										IPageLayout.ID_PROP_SHEET);
					} catch (PartInitException e1) {
					}
				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.FETCH) {

			final String configName = (String) node.getParent().getObject();

			MenuItem doFetch = new MenuItem(men, SWT.PUSH);
			doFetch.setText(UIText.RepositoriesView_DoFetchMenu);
			doFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent evt) {
					new FetchConfiguredRemoteAction(node.getRepository(),
							configName).run(getSite().getShell());
				}

			});

			MenuItem configureUrlFetch = new MenuItem(men, SWT.PUSH);
			configureUrlFetch
					.setText(UIText.RepositoriesView_ConfigureFetchMenu);

			configureUrlFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, false));
					if (dlg.open() == Window.OK)
						tv.refresh();

				}

			});

			MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
			deleteFetch.setText(UIText.RepositoriesView_RemoveFetch_menu);
			deleteFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryConfig config = node.getRepository().getConfig();
					config.unset("remote", configName, "url"); //$NON-NLS-1$ //$NON-NLS-2$
					config.unset("remote", configName, "fetch"); //$NON-NLS-1$//$NON-NLS-2$
					try {
						config.save();
						tv.refresh();
					} catch (IOException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_ErrorHeader, e1
										.getMessage());
					}
				}

			});

		}

		if (node.getType() == RepositoryTreeNodeType.PUSH) {

			final String configName = (String) node.getParent().getObject();

			MenuItem doPush = new MenuItem(men, SWT.PUSH);
			doPush.setText(UIText.RepositoriesView_DoPushMenuItem);
			doPush.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent evt) {
					new PushConfiguredRemoteAction(node.getRepository(),
							configName).run(getSite().getShell(), false);
				}
			});

			MenuItem configureUrlPush = new MenuItem(men, SWT.PUSH);
			configureUrlPush.setText(UIText.RepositoriesView_ConfigurePushMenu);

			configureUrlPush.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, true));
					if (dlg.open() == Window.OK)
						tv.refresh();
				}

			});

			MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
			deleteFetch.setText(UIText.RepositoriesView_RemovePush_menu);
			deleteFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryConfig config = node.getRepository().getConfig();
					config.unset("remote", configName, "pushurl"); //$NON-NLS-1$ //$NON-NLS-2$
					config.unset("remote", configName, "push"); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						config.save();
						tv.refresh();
					} catch (IOException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_ErrorHeader, e1
										.getMessage());
					}
				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.FILE) {

			final File file = (File) node.getObject();

			MenuItem openInTextEditor = new MenuItem(men, SWT.PUSH);
			openInTextEditor
					.setText(UIText.RepositoriesView_OpenInTextEditor_menu);
			openInTextEditor.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					openFile(file);
				}

			});

			new MenuItem(men, SWT.SEPARATOR);
			createCopyPathItem(men, file.getPath());
		}

		if (!isBare && node.getType() == RepositoryTreeNodeType.WORKINGDIR) {
			String path = node.getRepository().getWorkDir().getAbsolutePath();
			createImportProjectItem(men, node.getRepository(), path);
			new MenuItem(men, SWT.SEPARATOR);
			createCopyPathItem(men, path);
		}

		if (node.getType() == RepositoryTreeNodeType.FOLDER) {
			String path = ((File) node.getObject()).getPath();
			createImportProjectItem(men, node.getRepository(), path);
			new MenuItem(men, SWT.SEPARATOR);
			createCopyPathItem(men, path);
		}

	}

	private boolean isBare(Repository repository) {
		return repository.getConfig().getBoolean("core", "bare", false); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private boolean isRefCheckedOut(Repository repository, String refName) {
		String branchName;
		String compareString;

		try {
			branchName = repository.getFullBranch();
			if (branchName == null)
				return false;
			if (refName.startsWith(Constants.R_HEADS)) {
				compareString = refName;
			} else if (refName.startsWith(Constants.R_TAGS)) {
				compareString = repository.mapTag(refName).getObjId().getName();
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				compareString = repository.mapCommit(refName).getCommitId()
						.getName();
			} else {
				return false;
			}
		} catch (IOException e1) {
			return false;
		}

		return compareString.equals(branchName);
	}

	private void createCopyPathItem(Menu men, final String path) {

		MenuItem copyPath;
		copyPath = new MenuItem(men, SWT.PUSH);
		copyPath.setText(UIText.RepositoriesView_CopyPathToClipboardMenu);
		copyPath.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Clipboard clipboard = new Clipboard(null);
				TextTransfer textTransfer = TextTransfer.getInstance();
				Transfer[] transfers = new Transfer[] { textTransfer };
				Object[] data = new Object[] { path };
				clipboard.setContents(data, transfers);
				clipboard.dispose();
			}

		});

	}

	private void createCreateBranchItem(Menu men, final RepositoryTreeNode node) {
		final Ref ref;
		if (node.getType() == RepositoryTreeNodeType.REF)
			ref = (Ref) node.getObject();
		else
			ref = null;

		MenuItem createLocal = new MenuItem(men, SWT.PUSH);
		createLocal.setText(UIText.RepositoriesView_NewBranchMenu);

		createLocal.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Wizard wiz = new Wizard() {

					@Override
					public void addPages() {
						addPage(new CreateBranchPage(node.getRepository(), ref));
						setWindowTitle(UIText.RepositoriesView_NewBranchTitle);
					}

					@Override
					public boolean performFinish() {

						try {
							getContainer().run(false, true,
									new IRunnableWithProgress() {

										public void run(IProgressMonitor monitor)
												throws InvocationTargetException,
												InterruptedException {
											CreateBranchPage cp = (CreateBranchPage) getPages()[0];
											try {
												cp.createBranch(monitor);
											} catch (CoreException ce) {
												throw new InvocationTargetException(
														ce);
											} catch (IOException ioe) {
												throw new InvocationTargetException(
														ioe);
											}

										}
									});
						} catch (InvocationTargetException ite) {
							Activator
									.handleError(
											UIText.RepositoriesView_BranchCreationFailureMessage,
											ite.getCause(), true);
							return false;
						} catch (InterruptedException ie) {
						}
						return true;
					}
				};
				if (new WizardDialog(getSite().getShell(), wiz).open() == Window.OK)
					tv.refresh();
			}

		});
	}

	private void createDeleteBranchItem(Menu men, final RepositoryTreeNode node) {

		final Ref ref = (Ref) node.getObject();

		MenuItem deleteBranch = new MenuItem(men, SWT.PUSH);
		deleteBranch.setText(UIText.RepositoriesView_DeleteBranchMenu);

		deleteBranch.setEnabled(!isRefCheckedOut(node.getRepository(), ref
				.getName()));

		deleteBranch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (!MessageDialog
						.openConfirm(
								getSite().getShell(),
								UIText.RepositoriesView_ConfirmDeleteTitle,
								NLS
										.bind(
												UIText.RepositoriesView_ConfirmBranchDeletionMessage,
												ref.getName())))
					return;

				try {
					new ProgressMonitorDialog(getSite().getShell()).run(false,
							false, new IRunnableWithProgress() {

								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {

									try {
										RefUpdate op = node.getRepository()
												.updateRef(ref.getName());
										op.setRefLogMessage("branch deleted", //$NON-NLS-1$
												false);
										op.setForceUpdate(true);
										op.delete();
										tv.refresh();
									} catch (IOException ioe) {
										throw new InvocationTargetException(ioe);
									}

								}
							});
				} catch (InvocationTargetException e1) {
					Activator
							.handleError(
									UIText.RepositoriesView_BranchDeletionFailureMessage,
									e1.getCause(), true);
					e1.printStackTrace();
				} catch (InterruptedException e1) {
				}
			}

		});

	}

	private void openFile(File file) {
		IFileStore store = EFS.getLocalFileSystem().getStore(
				new Path(file.getAbsolutePath()));
		try {
			IDE.openEditor(getSite().getPage(),
					new FileStoreEditorInput(store),
					EditorsUI.DEFAULT_TEXT_EDITOR_ID);
		} catch (PartInitException e) {
			Activator.handleError(UIText.RepositoriesView_Error_WindowTitle, e,
					true);
		}
	}

	private void checkoutBranch(final RepositoryTreeNode node,
			final String refName) {
		Job job = new Job(NLS.bind(UIText.RepositoriesView_CheckingOutMessage,
				refName)) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				Repository repo = node.getRepository();

				final BranchOperation op = new BranchOperation(repo, refName);
				IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

					public void run(IProgressMonitor myMonitor)
							throws CoreException {
						op.execute(myMonitor);
					}
				};

				try {
					ResourcesPlugin.getWorkspace().run(wsr,
							ResourcesPlugin.getWorkspace().getRoot(),
							IWorkspace.AVOID_UPDATE, monitor);
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							tv.refresh();
						}
					});

				} catch (CoreException e1) {
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							e1.getMessage(), e1);
				}

				return Status.OK_STATUS;
			}
		};

		job.setUser(true);
		job.schedule();
	}

	private void createImportProjectItem(Menu men, final Repository repo,
			final String path) {

		MenuItem startWizard;
		startWizard = new MenuItem(men, SWT.PUSH);
		startWizard.setText(UIText.RepositoriesView_ImportProjectsMenu);
		startWizard.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				WizardDialog dlg = new WizardDialog(getSite().getShell(),
						new GitCreateProjectViaWizardWizard(repo, path));
				dlg.open();
			}

		});

	}

	private void addActionsToToolbar() {

		IToolBarManager manager = getViewSite().getActionBars()
				.getToolBarManager();

		refreshAction = new Action(UIText.RepositoriesView_Refresh_Button) {

			@Override
			public void run() {
				scheduleRefresh();
			}
		};
		refreshAction.setImageDescriptor(UIIcons.ELCL16_REFRESH);
		manager.add(refreshAction);

		linkWithSelectionAction = new Action(
				UIText.RepositoriesView_LinkWithSelection_action,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				IEclipsePreferences prefs = getPrefs();
				prefs.putBoolean(PREFS_SYNCED, isChecked());
				try {
					prefs.flush();
				} catch (BackingStoreException e) {
				}
				if (isChecked()) {
					ISelectionService srv = (ISelectionService) getSite()
							.getService(ISelectionService.class);
					reactOnSelection(srv.getSelection());
				}

			}
