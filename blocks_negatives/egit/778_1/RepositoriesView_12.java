		}

		return super.getAdapter(adapter);
	}

	@Override
	public void createPartControl(Composite parent) {
		tv = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tv.setContentProvider(new RepositoriesViewContentProvider());
		new RepositoriesViewLabelProvider(tv);

		getSite().setSelectionProvider(this);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				copyAction.setEnabled(false);

				IStructuredSelection ssel = (IStructuredSelection) event
						.getSelection();
				if (ssel.size() == 1) {
					RepositoryTreeNode node = (RepositoryTreeNode) ssel
							.getFirstElement();
					if (node.getType() == RepositoryTreeNodeType.REPO
							|| node.getType() == RepositoryTreeNodeType.WORKINGDIR
							|| node.getType() == RepositoryTreeNodeType.FOLDER
							|| node.getType() == RepositoryTreeNodeType.FILE) {
						copyAction.setEnabled(true);
					}
					setSelection(new StructuredSelection(ssel.getFirstElement()));
				} else {
					setSelection(new StructuredSelection());
				}

			}
		});
		tv.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty()) {
					return;
				}

				Object element = selection.getFirstElement();
				ITreeContentProvider contentProvider = (ITreeContentProvider) tv
						.getContentProvider();
				if (contentProvider.hasChildren(element)) {
					tv.setExpandedState(element, !tv.getExpandedState(element));
				} else {
					Object[] selectionArray = selection.toArray();
					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						if (node.getType() != RepositoryTreeNodeType.FILE
								&& node.getType() != RepositoryTreeNodeType.REF
								&& node.getType() != RepositoryTreeNodeType.TAG) {
							return;
						}
					}

					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						if (node.getType() == RepositoryTreeNodeType.FILE)
							openFile((File) node.getObject());
						else if (node.getType() == RepositoryTreeNodeType.REF
								|| node.getType() == RepositoryTreeNodeType.TAG) {
							Ref ref = (Ref) node.getObject();
							if (!isBare(node.getRepository())
									&& ref.getName().startsWith(
											Constants.R_REFS))
								checkoutBranch(node, ref.getName());
						}
					}
				}
			}
		});

		addContextMenu();

		addActionsToToolbar();

		scheduleRefresh();


			@Override
			protected IStatus run(IProgressMonitor monitor) {
				scheduleRefresh();
				schedule(AUTO_REFRESH_INTERVAL_MILLISECONDS);
				return Status.OK_STATUS;
			}
		};

		autoRefreshJob.setSystem(true);
		autoRefreshJob.schedule(AUTO_REFRESH_INTERVAL_MILLISECONDS);

		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.addPostSelectionListener(new ISelectionListener() {

			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {

				if (linkWithSelectionAction == null
						|| !linkWithSelectionAction.isChecked())
					return;

				if (part instanceof IEditorPart) {
					IEditorInput input = ((IEditorPart) part).getEditorInput();
					if (input instanceof IFileEditorInput)
						reactOnSelection(new StructuredSelection(
								((IFileEditorInput) input).getFile()));

				} else {
					reactOnSelection(selection);
				}
			}

		});
	}

	private void reactOnSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			if (ssel.size() != 1)
				return;
			if (ssel.getFirstElement() instanceof IResource) {
				showResource((IResource) ssel.getFirstElement());
			}
			if (ssel.getFirstElement() instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) ssel
						.getFirstElement()).getAdapter(IResource.class);
				if (adapted != null)
					showResource(adapted);
			}
		}
	}

	private void addContextMenu() {
		tv.getTree().addMenuDetectListener(new MenuDetectListener() {

			public void menuDetected(MenuDetectEvent e) {
				Menu men = tv.getTree().getMenu();
				if (men != null) {
					men.dispose();
				}
				men = new Menu(tv.getTree());

				TreeItem testItem = tv.getTree().getItem(
						tv.getTree().toControl(new Point(e.x, e.y)));
				if (testItem == null) {
					addMenuItemsForPanel(men);
				} else {
					addMenuItemsForTreeSelection(men);
				}

				tv.getTree().setMenu(men);
			}
		});
	}

	private void addMenuItemsForPanel(Menu men) {

		MenuItem importItem = new MenuItem(men, SWT.PUSH);
		importItem.setText(UIText.RepositoriesView_ImportRepository_MenuItem);
		importItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				importAction.run();
			}

		});

		MenuItem addItem = new MenuItem(men, SWT.PUSH);
		addItem.setText(UIText.RepositoriesView_AddRepository_MenuItem);
		addItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				addAction.run();
			}

		});

		MenuItem pasteItem = new MenuItem(men, SWT.PUSH);
		pasteItem.setText(UIText.RepositoriesView_PasteMenu);
		pasteItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				pasteAction.run();
			}

		});

		MenuItem refreshItem = new MenuItem(men, SWT.PUSH);
		refreshItem.setText(refreshAction.getText());
		refreshItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshAction.run();
			}

		});

	}

	private void addMenuItemsForTreeSelection(Menu men) {

		final IStructuredSelection sel = (IStructuredSelection) tv
				.getSelection();

		boolean repoOnly = true;
		for (Object selected : sel.toArray()) {

			if (((RepositoryTreeNode) selected).getType() != RepositoryTreeNodeType.REPO) {
				repoOnly = false;
				break;
			}
		}

		if (sel.size() > 1 && repoOnly) {
			List nodes = sel.toList();
			final Repository[] repos = new Repository[nodes.size()];
			for (int i = 0; i < sel.size(); i++)
				repos[i] = ((RepositoryTreeNode) nodes.get(i)).getRepository();

			MenuItem remove = new MenuItem(men, SWT.PUSH);
			remove.setText(UIText.RepositoriesView_Remove_MenuItem);
			remove.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					removeRepository(new NullProgressMonitor(), repos);
				}
			});

		}

		if (sel.size() > 1)
			return;

		final RepositoryTreeNode node = (RepositoryTreeNode) sel
				.getFirstElement();

		final boolean isBare = isBare(node.getRepository());

		if (node.getType() == RepositoryTreeNodeType.REF) {

			final Ref ref = (Ref) node.getObject();

			if (!ref.isSymbolic()) {

				if (!isBare) {
					MenuItem checkout = new MenuItem(men, SWT.PUSH);
					checkout.setText(UIText.RepositoriesView_CheckOut_MenuItem);

					checkout.setEnabled(!isRefCheckedOut(node.getRepository(),
							ref.getName()));

					checkout.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							checkoutBranch(node, ref.getLeaf().getName());
						}
					});

					new MenuItem(men, SWT.SEPARATOR);
				}

				createCreateBranchItem(men, node);
				createDeleteBranchItem(men, node);

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

		};

		linkWithSelectionAction
				.setToolTipText(UIText.RepositoriesView_LinkWithSelection_action);
		linkWithSelectionAction.setImageDescriptor(UIIcons.ELCL16_SYNCED);
		linkWithSelectionAction.setChecked(getPrefs().getBoolean(PREFS_SYNCED,
				false));

		manager.add(linkWithSelectionAction);

		manager.add(new Separator());

		IAction collapseAllAction = new Action(
				UIText.RepositoriesView_CollapseAllMenu) {

			@Override
			public void run() {
				tv.collapseAll();
			}
		};
		collapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);
		manager.add(collapseAllAction);

		manager.add(new Separator());

		importAction = new Action(UIText.RepositoriesView_Import_Button) {

			@Override
			public void run() {
				WizardDialog dlg = new WizardDialog(getSite().getShell(),
						new GitCloneWizard());
				if (dlg.open() == Window.OK)
					scheduleRefresh();
			}
		};
		importAction.setToolTipText(UIText.RepositoriesView_Clone_Tooltip);
		importAction.setImageDescriptor(UIIcons.CLONEGIT);

		manager.add(importAction);

		addAction = new Action(UIText.RepositoriesView_Add_Button) {

			@Override
			public void run() {
				RepositorySearchDialog sd = new RepositorySearchDialog(
						getSite().getShell(), getDirs());
				if (sd.open() == Window.OK) {
					Set<String> dirs = new HashSet<String>();
					dirs.addAll(getDirs());
					if (dirs.addAll(sd.getDirectories()))
						saveDirs(dirs);
					scheduleRefresh();
				}

			}
		};
		addAction.setToolTipText(UIText.RepositoriesView_AddRepository_Tooltip);
		addAction.setImageDescriptor(UIIcons.NEW_REPOSITORY);

		manager.add(addAction);

