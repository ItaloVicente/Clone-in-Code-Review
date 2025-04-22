			Rectangle clientArea = composite.getClientArea();

			Control[] ws = composite.getChildren();


			for (Control w : ws) {
				if (w == seperator1) { // Separator
					Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
							flushCache);
					w.setBounds(clientArea.x, clientArea.y, clientArea.width,
							e.y);
					clientArea.y += e.y;
					clientArea.height -= e.y;
				} else if (getToolBarControl() == w) {
					if (toolBarChildrenExist()) {
						Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
								flushCache);
						w.setBounds(clientArea.x, clientArea.y,
								clientArea.width, e.y);
						clientArea.y += e.y + VGAP;
						clientArea.height -= e.y + VGAP;
					}
				} else if (getCoolBarControl() == w) {
					if (coolBarChildrenExist()) {
						Point e = w.computeSize(clientArea.width, SWT.DEFAULT,
								flushCache);
						w.setBounds(clientArea.x, clientArea.y,
								clientArea.width, e.y);
						clientArea.y += e.y + VGAP;
						clientArea.height -= e.y + VGAP;
					}
				} else if (statusLineManager != null
						&& statusLineManager.getControl() == w) {
					Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
							flushCache);
					w.setBounds(clientArea.x, clientArea.y + clientArea.height
							- e.y, clientArea.width, e.y);
					clientArea.height -= e.y + VGAP;
				} else {
					w.setBounds(clientArea.x, clientArea.y + VGAP,
							clientArea.width, clientArea.height - VGAP);
				}
			}
		}
	}

	protected Label getSeperator1() {
		return seperator1;
	}

	public ApplicationWindow(Shell parentShell) {
		super(parentShell);
	}

	protected void addMenuBar() {
		if ((getShell() == null) && (menuBarManager == null)) {
			menuBarManager = createMenuManager();
		}
	}

	protected void addStatusLine() {
		if ((getShell() == null) && (statusLineManager == null)) {
			statusLineManager = createStatusLineManager();
		}
	}

	protected void addToolBar(int style) {
		if ((getShell() == null) && (toolBarManager == null)
				&& (coolBarManager == null)) {
			toolBarManager = createToolBarManager2(style);
		}
	}

	protected void addCoolBar(int style) {
		if ((getShell() == null) && (toolBarManager == null)
				&& (coolBarManager == null)) {
			coolBarManager = createCoolBarManager2(style);
		}
	}

	@Override
