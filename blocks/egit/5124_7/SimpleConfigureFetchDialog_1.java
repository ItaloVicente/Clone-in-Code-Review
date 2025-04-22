			Composite branchArea = new Composite(main, SWT.NONE);
			GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
					.applyTo(branchArea);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchArea);

			Label branchLabel = new Label(branchArea, SWT.NONE);
