        canvas.addPaintListener(new PaintListener() {
            @Override
			public void paintControl(PaintEvent event) {

                GC gc = event.gc;
                Transform transform = null;
                if (orientation == SWT.VERTICAL) {
	                transform = new Transform(event.display);
	            	transform.translate(TrimUtil.TRIM_DEFAULT_HEIGHT, 0);
	            	transform.rotate(90);
                }
                ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();

                int itemCount = Math.min(displayedItems.length, numShowItems);

                int yOffset = 0;
                int xOffset = 0;
                    Rectangle clientArea = canvas.getParent().getClientArea();
                    if (orientation == SWT.HORIZONTAL) {
                    	int size = clientArea.height;
	                    yOffset = size - (fontMetrics.getHeight());
	                    yOffset = yOffset / 2;
                    } else {
                    	int size = clientArea.width;
                    	xOffset = size - (fontMetrics.getHeight());
                    	xOffset = xOffset / 2;
                    }
                }

                for (int i = 0; i < itemCount; i++) {
                    String string = labelProvider.getText(displayedItems[i]);
                    if(string == null) {
					}
                    if (orientation == SWT.HORIZONTAL) {
                    	gc.drawString(string, 2, yOffset + (i * fontMetrics.getHeight()), true);
                    } else {
		            	gc.setTransform(transform);
                    	gc.drawString(string, xOffset + (i * fontMetrics.getHeight()), 2, true);
                    }
                }
                if (transform != null)
                	transform.dispose();
            }
        });
