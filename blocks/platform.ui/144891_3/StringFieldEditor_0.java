				if (widthInChars!=UNLIMITED) {
					Point extent = gc.textExtent("X");//$NON-NLS-1$
					gd.widthHint = widthInChars * extent.x;
				} else {
					gd.horizontalAlignment = GridData.FILL;
					gd.grabExcessHorizontalSpace = true;
				}
				if (heigthInChars>1) {
					gd.heightHint = heigthInChars * gc.getFontMetrics().getHeight();
				}
