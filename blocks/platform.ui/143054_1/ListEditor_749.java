		} else {
			checkParent(buttonBox, parent);
		}

		selectionChanged();
		return buttonBox;
	}

	public List getListControl(Composite parent) {
		if (list == null) {
			list = new List(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL
					| SWT.H_SCROLL);
			list.setFont(parent.getFont());
			list.addSelectionListener(getSelectionListener());
			list.addDisposeListener(event -> list = null);
		} else {
			checkParent(list, parent);
		}
		return list;
	}

	protected abstract String getNewInputObject();

	@Override
