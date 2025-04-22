			ListViewer listOfInts = new ListViewer(addRemoveComposite,
					SWT.BORDER);

			listOfInts.setContentProvider(new ObservableSetContentProvider<>());
			listOfInts.setLabelProvider(new ViewerLabelProvider());
			listOfInts.setInput(inputSet);

			final IObservableValue<Double> selectedInt = ViewerProperties.singleSelection(Double.class)
					.observe(listOfInts);

			GridData listData = new GridData(GridData.FILL_BOTH);
			listData.minimumHeight = 1;
			listData.minimumWidth = 1;
			listData.widthHint = 150;
			listData.heightHint = 150;
			listOfInts.getControl().setLayoutData(listData);

			Composite buttonBar = new Composite(addRemoveComposite, SWT.NONE);

				Button add = new Button(buttonBar, SWT.PUSH);
				add.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						inputSet.add(random.nextDouble() * 100.0);
						super.widgetSelected(e);
					}
				});
				add.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
