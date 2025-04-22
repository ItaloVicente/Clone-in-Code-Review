            infoArea.addListener(SWT.Resize, event -> {
			    int w = scrolledComposite.getClientArea().width;
			    if (w < WRAP_MIN_WIDTH) {
			        w = WRAP_MIN_WIDTH;
			    }
			    for (int i = 0; i < texts.size(); i++) {
			        int extent;
			        if (i == 0) {
						extent = w - adjustFirst;
					} else {
						extent = w - adjust;
					}
			        StyledText text = (StyledText) texts.get(i);
			        Point p1 = text.computeSize(extent, SWT.DEFAULT, false);
			        ((GridData) text.getLayoutData()).widthHint = p1.x;
			    }
			    Point p2 = infoArea.computeSize(SWT.DEFAULT, SWT.DEFAULT,
			            true);
			    scrolledComposite.setMinHeight(p2.y);
			});
