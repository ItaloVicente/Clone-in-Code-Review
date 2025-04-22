		if (clientSite == null)
			return;
		WorkspaceModifyOperation op = saveNewFileOperation();
		Shell shell = clientSite.getShell();
		try {
			new ProgressMonitorDialog(shell).run(false, true, op);
		} catch (InterruptedException interrupt) {
		} catch (InvocationTargetException invocationException) {
			MessageDialog.openError(shell, RENAME_ERROR_TITLE,
					invocationException.getTargetException().getMessage());
		}

	}

	public OleClientSite getClientSite() {
		return clientSite;
	}

	public File getSourceFile() {
		return source;
	}

	private void handleWord() {
		OleAutomation dispInterface = new OleAutomation(clientSite);
		int[] appId = dispInterface
				.getIDsOfNames(new String[] { "Application" }); //$NON-NLS-1$
		if (appId != null) {
			Variant pVarResult = dispInterface.getProperty(appId[0]);
			if (pVarResult != null) {
				OleAutomation application = pVarResult.getAutomation();
				int[] dispid = application
						.getIDsOfNames(new String[] { "DisplayScrollBars" }); //$NON-NLS-1$
				if (dispid != null) {
					Variant rgvarg = new Variant(true);
					application.setProperty(dispid[0], rgvarg);
				}
				application.dispose();
			}
		}
		dispInterface.dispose();
	}

	@Override
