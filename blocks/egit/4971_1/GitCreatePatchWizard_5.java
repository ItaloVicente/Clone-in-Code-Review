		private IContainer wsSelectedContainer;

		class LocationPageContentProvider extends BaseWorkbenchContentProvider {
			boolean showClosedProjects=false;

			public Object[] getChildren(Object element) {
				if (element instanceof IWorkspace) {
					IProject[] allProjects = ((IWorkspace) element).getRoot().getProjects();
					if (showClosedProjects)
						return allProjects;

					ArrayList<IProject> accessibleProjects = new ArrayList<IProject>();
					for (int i = 0; i < allProjects.length; i++) {
						if (allProjects[i].isOpen()) {
							accessibleProjects.add(allProjects[i]);
						}
					}
					return accessibleProjects.toArray();
				}
				return super.getChildren(element);
			}
		}

		class WorkspaceDialog extends TitleAreaDialog {

			protected TreeViewer wsTreeViewer;
			protected Text wsFilenameText;
			protected Image dlgTitleImage;

			private boolean modified = false;

			public WorkspaceDialog(Shell shell) {
				super(shell);
			}

			protected Control createContents(Composite parent) {
				Control control = super.createContents(parent);
				setTitle(UIText.GitCreatePatchWizard_WorkspacePatchDialogTitle);
				setMessage(UIText.GitCreatePatchWizard_WorkspacePatchDialogDescription);
				dlgTitleImage = UIIcons.WIZBAN_CREATE_PATCH.createImage();
				setTitleImage(dlgTitleImage);

				return control;
			}

			protected Control createDialogArea(Composite parent){
				Composite parentComposite = (Composite) super.createDialogArea(parent);

				Composite composite = new Composite(parentComposite, SWT.NONE);
				GridLayout layout = new GridLayout();
				layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
				layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
				layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
				layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
				composite.setLayout(layout);
				composite.setLayoutData(new GridData(GridData.FILL_BOTH));
				composite.setFont(parentComposite.getFont());

				getShell().setText(UIText.GitCreatePatchWizard_WorkspacePatchDialogSavePatch);

				wsTreeViewer = new TreeViewer(composite, SWT.BORDER);
				final GridData gd= new GridData(SWT.FILL, SWT.FILL, true, true);
				gd.widthHint= 550;
				gd.heightHint= 250;
				wsTreeViewer.getTree().setLayoutData(gd);

				wsTreeViewer.setContentProvider(new LocationPageContentProvider());
				wsTreeViewer.setComparator(new ResourceComparator(ResourceComparator.NAME));
				wsTreeViewer.setLabelProvider(new WorkbenchLabelProvider());
				wsTreeViewer.setInput(ResourcesPlugin.getWorkspace());

				IPath existingWorkspacePath = new Path(wsPathText.getText());
				IResource selectedResource = ResourcesPlugin.getWorkspace().getRoot().findMember(existingWorkspacePath);
				if (selectedResource != null) {
					wsTreeViewer.expandToLevel(selectedResource, 0);
					wsTreeViewer.setSelection(new StructuredSelection(selectedResource));
				}

				final Composite group = new Composite(composite, SWT.NONE);
				layout = new GridLayout(2, false);
				layout.marginWidth = 0;
				group.setLayout(layout);
				group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

				final Label label = new Label(group, SWT.NONE);
				label.setLayoutData(new GridData());
				label.setText(UIText.GitCreatePatchWizard_WorkspacePatchDialogFileName);

				wsFilenameText = new Text(group,SWT.BORDER);
				wsFilenameText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

				setupListeners();

				return parent;
			}

			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				Button button = super.createButton(parent, id, label,
						defaultButton);
				if (id == IDialogConstants.OK_ID) {
					button.setEnabled(false);
				}
				return button;
			}

			private void validateDialog() {
				String fileName = wsFilenameText.getText();

				if (fileName.equals("")) { //$NON-NLS-1$
					if (modified) {
						setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchDialogEnterFileName);
						getButton(IDialogConstants.OK_ID).setEnabled(false);
						return;
					} else {
						setErrorMessage(null);
						getButton(IDialogConstants.OK_ID).setEnabled(false);
						return;
					}
				}

				if (!(ResourcesPlugin.getWorkspace().validateName(fileName,
						IResource.FILE)).isOK() && modified) {
					setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchEnterValidFileName);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				}

				if (getSelectedContainer() == null) {
					setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchDialogEnterValidLocation);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				} else {
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IPath fullPath = wsSelectedContainer.getFullPath().append(
							fileName);
					if (workspace.getRoot().getFolder(fullPath).exists()) {
						setErrorMessage(UIText.GitCreatePatchWizard_WorkspacePatchFolderExists);
						getButton(IDialogConstants.OK_ID).setEnabled(false);
						return;
					}
				}

				setErrorMessage(null);
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			}

			protected void okPressed() {
				IFile file = wsSelectedContainer.getFile(new Path(
						wsFilenameText.getText()));
				if (file != null)
					wsPathText.setText(file.getFullPath().toString());

				validatePage();
				super.okPressed();
			}

			private IContainer getSelectedContainer() {
				Object obj = ((IStructuredSelection)wsTreeViewer.getSelection()).getFirstElement();
				if (obj instanceof IContainer) {
					wsSelectedContainer = (IContainer) obj;
				} else if (obj instanceof IFile) {
					wsSelectedContainer = ((IFile) obj).getParent();
				}
				return wsSelectedContainer;
			}

			protected void cancelPressed() {
				validatePage();
				super.cancelPressed();
			}

			public boolean close() {
				if (dlgTitleImage != null)
					dlgTitleImage.dispose();
				return super.close();
			}

			void setupListeners(){
				wsTreeViewer.addSelectionChangedListener(
						new ISelectionChangedListener() {
							public void selectionChanged(SelectionChangedEvent event) {
								IStructuredSelection s = (IStructuredSelection)event.getSelection();
								Object obj=s.getFirstElement();
								if (obj instanceof IContainer)
									wsSelectedContainer = (IContainer) obj;
								else if (obj instanceof IFile){
									IFile tempFile = (IFile) obj;
									wsSelectedContainer = tempFile.getParent();
									wsFilenameText.setText(tempFile.getName());
								}
								validateDialog();
							}
						});

				wsTreeViewer.addDoubleClickListener(
						new IDoubleClickListener() {
							public void doubleClick(DoubleClickEvent event) {
								ISelection s= event.getSelection();
								if (s instanceof IStructuredSelection) {
									Object item = ((IStructuredSelection)s).getFirstElement();
									if (wsTreeViewer.getExpandedState(item))
										wsTreeViewer.collapseToLevel(item, 1);
									else
										wsTreeViewer.expandToLevel(item, 1);
								}
								validateDialog();
							}
						});

				wsFilenameText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						modified = true;
						validateDialog();
					}
				});
			}
		}

