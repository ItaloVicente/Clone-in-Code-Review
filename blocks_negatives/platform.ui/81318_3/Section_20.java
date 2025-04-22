			Listener listener = new Listener() {
				@Override
				public void handleEvent(Event e) {
					Image image = Section.super.getBackgroundImage();
					if (image != null) {
						FormImages.getInstance().markFinished(image, getDisplay());
					}
					Section.super.setBackgroundImage(null);
