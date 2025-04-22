			@Override
			public void controlResized(ControlEvent e) {
				org.eclipse.swt.graphics.Rectangle b = parent.getBounds();
				int oldOrientation = horizontalSashForm.getOrientation();
				if ((oldOrientation == SWT.HORIZONTAL)
						&& (b.height > b.width)) {
					horizontalSashForm.setOrientation(SWT.VERTICAL);
					horizontalSashForm.setWeights(defaultWeights);
				} else if ((oldOrientation == SWT.VERTICAL)
						&& (b.height <= b.width)) {
					horizontalSashForm.setOrientation(SWT.HORIZONTAL);
					horizontalSashForm.setWeights(defaultWeights);
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
