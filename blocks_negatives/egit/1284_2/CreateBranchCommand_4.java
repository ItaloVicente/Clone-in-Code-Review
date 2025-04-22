
		Wizard wiz = new Wizard() {

			@Override
			public void addPages() {
				addPage(new CreateBranchPage(node.getRepository(), baseBranch));
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
										throw new InvocationTargetException(ce);
									} catch (IOException ioe) {
										throw new InvocationTargetException(ioe);
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
		new WizardDialog(getShell(event), wiz).open();

