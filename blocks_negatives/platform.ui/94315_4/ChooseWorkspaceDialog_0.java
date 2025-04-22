			Composite recentWorkspacePanel = new Composite(panel, SWT.NONE);
			recentWorkspacesComposites.put(recentWorkspace, recentWorkspacePanel);
			GridLayout recentWorkspacePanelLayout = new GridLayout(3, false);
			recentWorkspacePanel.setLayout(recentWorkspacePanelLayout);

			Link link = new Link(recentWorkspacePanel, SWT.WRAP);
			link.setLayoutData(new GridData(500, SWT.DEFAULT));
