	      GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		  gd.widthHint = 400;
		  gd.heightHint = 500;
		  fTree.setLayoutData(gd);
		  fTree.getViewer().setContentProvider(new TestModelContentProvider());
		  fTree.getViewer().setLabelProvider(new LabelProvider());
