			MessageDialog.openInformation(shell, MessageUtil.getString("Readme_Outline"), //$NON-NLS-1$
					MessageUtil.getString("ReadmeOutlineActionExecuted")); //$NON-NLS-1$
		}
	}

	public ReadmeContentOutlinePage(IFile input) {
		super();
		this.input = input;
	}

	@Override
