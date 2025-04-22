
		ControlContribution controlContribution = new ControlContribution(
				"StagingView.searchText") { //$NON-NLS-1$
			@Override
			protected Control createControl(Composite parent) {
				Composite toolbarComposite = toolkit.createComposite(parent,
						SWT.NONE);
				toolbarComposite.setBackground(null);
				GridLayout headLayout = new GridLayout();
				headLayout.numColumns = 2;
				headLayout.marginHeight = 0;
				headLayout.marginWidth = 0;
				headLayout.marginTop = 0;
				headLayout.marginBottom = 0;
				headLayout.marginLeft = 0;
				headLayout.marginRight = 0;
				toolbarComposite.setLayout(headLayout);

				return toolbarComposite;
			}
		};

