		parent.addControlListener(new ControlListener() {

			private int[] defaultWeights = { 1, 1 };

			@Override
			public void controlResized(ControlEvent e) {
				org.eclipse.swt.graphics.Rectangle b = parent.getBounds();
				int oldOrientation = mainSashForm.getOrientation();
				if ((oldOrientation == SWT.HORIZONTAL)
						&& (b.height > b.width)) {
					mainSashForm.setOrientation(SWT.VERTICAL);
					mainSashForm.setWeights(defaultWeights);
				} else if ((oldOrientation == SWT.VERTICAL)
						&& (b.height <= b.width)) {
					mainSashForm.setOrientation(SWT.HORIZONTAL);
					mainSashForm.setWeights(defaultWeights);
				}
			}
