		try {
			trComp.setRedraw(false);
			trComp.pack();
			trComp.requestLayout();
		} finally {
			trComp.setRedraw(true);
		}
