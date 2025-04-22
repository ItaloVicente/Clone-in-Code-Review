		if (this.enableAntialias)
			try {
				g.setAntialias(SWT.ON);
			} catch (SWTException e) {
				this.enableAntialias = false;
			}

