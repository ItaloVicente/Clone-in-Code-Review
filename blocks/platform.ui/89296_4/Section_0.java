				updateHeaderImage = true;
				if(e.type==SWT.Dispose){
					Image image = Section.super.getBackgroundImage();
					if (image != null) {
						FormImages.getInstance().markFinished(image, getDisplay());
					}
