			updateWidth(this);
			super.reflow(flushCache);
		}
	}

	public BrowserText(Composite parent, BrowserViewer viewer, Throwable ex) {
		this.viewer = viewer;
		this.ex = ex;
		Color bg = parent.getDisplay()
				.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		scomp = new ReflowScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite client = new Composite(scomp, SWT.NULL);
		fillContent(client, bg);
		scomp.setContent(client);
		scomp.setBackground(bg);
	}

	private void fillContent(Composite parent, Color bg) {
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		parent.setLayout(layout);
		title = new Label(parent, SWT.WRAP);
		title.setText(Messages.BrowserText_title);
		title.setFont(JFaceResources.getHeaderFont());
		title.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		title.setBackground(bg);

		link = new Link(parent, SWT.WRAP);
		link.setText(Messages.BrowserText_link);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.setToolTipText(Messages.BrowserText_tooltip);
