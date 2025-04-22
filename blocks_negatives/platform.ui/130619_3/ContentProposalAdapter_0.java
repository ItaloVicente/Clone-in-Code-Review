			if (USE_VIRTUAL) {
				proposalTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.VIRTUAL);

				Listener listener = event -> handleSetData(event);
				proposalTable.addListener(SWT.SetData, listener);
			} else {
				proposalTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL);
			}
