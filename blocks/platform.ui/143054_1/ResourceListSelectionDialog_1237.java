		}
	}

	public ResourceListSelectionDialog(Shell parentShell, IResource[] resources) {
		super(parentShell);
		gatherResourcesDynamically = false;
		initDescriptors(resources);
	}

	public ResourceListSelectionDialog(Shell parentShell, IContainer container, int typeMask) {
		super(parentShell);
		this.container = container;
		this.typeMask = typeMask;
	}

	protected String adjustPattern() {
		String text = pattern.getText().trim();
		if (text.endsWith("<")) { //$NON-NLS-1$
			return text.substring(0, text.length() - 1);
		}
		if (!text.isEmpty() && !text.endsWith("*")) { //$NON-NLS-1$
			return text + "*"; //$NON-NLS-1$
		}
		return text;
	}

	@Override
