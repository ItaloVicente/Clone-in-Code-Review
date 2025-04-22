					}
				}
			});
		}
	}

	private void createImage(Composite parent) {
		descImageCanvas = new CLabel(parent, SWT.NONE);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
		data.widthHint = 0;
		data.heightHint = 0;
		descImageCanvas.setLayoutData(data);

		descImageCanvas.addDisposeListener(e -> {
			for (Iterator i = imageTable.values().iterator(); i.hasNext();) {
				((Image) i.next()).dispose();
			}
			imageTable.clear();
