		private Scale createLabeledScaleControl(Composite parent) {

			final int[] values = new int[] { 0, 1, 2, 3, 5, 10, 15, 20, 50,
					100, Integer.MAX_VALUE };

			Composite composite = SWTUtils.createHVFillComposite(parent,
					SWTUtils.MARGINS_DEFAULT);

			Composite labels = SWTUtils.createHVFillComposite(composite,
					SWTUtils.MARGINS_NONE, values.length);
			GridLayout labelsLayout = (GridLayout) labels.getLayout();
			labelsLayout.makeColumnsEqualWidth = true;
			labelsLayout.horizontalSpacing = 0;
			labels.setLayoutData(SWTUtils.createGridData(-1, -1, SWT.FILL,
					SWT.FILL, false, false));

			for (int i = 0; i < values.length; ++i) {
				Label label = SWTUtils.createLabel(labels, "" + values[i]); //$NON-NLS-1$
				if (i == 0) {
					label.setAlignment(SWT.LEFT);
				} else if (i == values.length - 1) {
					label.setAlignment(SWT.RIGHT);
				} else {
					label.setAlignment(SWT.CENTER);
				}
			}

			final Scale scale = new Scale(composite, SWT.HORIZONTAL);
			scale.setLayoutData(SWTUtils.createHFillGridData());
			scale.setMaximum(values.length - 1);
			scale.setMinimum(0);
			scale.setIncrement(1);
			scale.setPageIncrement(1);

			scale.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					scale.setSelection(scale.getSelection());
				}
			});

			return scale;
		}
