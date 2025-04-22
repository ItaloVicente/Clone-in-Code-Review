		list = new TableViewer(content, (multi ? SWT.MULTI : SWT.SINGLE)
				| SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);
		list.getTable().getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					@Override
					public void getName(AccessibleEvent e) {
						if (e.childID == ACC.CHILDID_SELF) {
							e.result = LegacyActionTools
									.removeMnemonics(listLabel.getText());
						}
					}
				});
