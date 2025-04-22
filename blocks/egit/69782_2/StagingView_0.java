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

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
