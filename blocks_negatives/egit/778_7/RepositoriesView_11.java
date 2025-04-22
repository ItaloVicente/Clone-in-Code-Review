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
