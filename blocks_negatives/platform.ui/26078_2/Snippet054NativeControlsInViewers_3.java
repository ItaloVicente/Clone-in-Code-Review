				item.setData("DISPOSELISTNER",listener);
			}

		});

		MyModel[] model = createModel(10);
		v.setInput(model);
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);

		Button b = new Button(shell,SWT.PUSH);
		b.setText("Modify input");
		b.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		b.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				v.setInput(createModel((int)(Math.random() * 10)));
