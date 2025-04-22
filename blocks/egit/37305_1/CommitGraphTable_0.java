		if (this.enableAntialias)
			try {
				event.gc.setAntialias(SWT.ON);
			} catch (SWTException e) {
				this.enableAntialias = false;
			}

