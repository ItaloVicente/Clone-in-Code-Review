				setFont();
			}
		});

		int index = addPage(composite);
		setPageText(index, MessageUtil.getString("Properties")); //$NON-NLS-1$
	}

	void createPage2() {
		Composite composite = new Composite(getContainer(), SWT.NONE);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(false);

		int index = addPage(composite);
		setPageText(index, MessageUtil.getString("Preview")); //$NON-NLS-1$
	}

	@Override
