			p.paint(imageGC, repaintRegion, resourceTable, selectedLink, selData);
		}
	}
	
	private class FormTextImageDescriptor extends CompositeImageDescriptor implements ImageDataProvider {
		private final int gcStyle;
		private final int width;
		private final int height;
		
		public FormTextImageDescriptor(int gcStyle, int width, int height) {
			this.gcStyle = gcStyle;
			this.width = width;
			this.height = height;
		}
		
		@Override
		public ImageData getImageData(int zoom) {
			Image zoomedImage = new Image(getDisplay(), zoom(width, zoom), zoom(height, zoom));
			GC zoomedGC = new GC(zoomedImage, gcStyle);
			
			Transform transform = new Transform(getDisplay());
			transform.identity();
			if (zoom != 100) {
				float scaleFactor = zoom / 100f;
				transform.scale(scaleFactor, scaleFactor);
				zoomedGC.setTransform(transform);
			}
			
			doRepaintOnImageGC(zoomedGC, 0, 0, width, height);
			
			ImageData result = zoomedImage.getImageData();
			
			zoomedGC.dispose();
			transform.dispose();
			zoomedImage.dispose();
			
			return result;
		}
		
		@Override
		protected void drawCompositeImage(int width, int height) {
			drawImage(this, 0, 0);
		}
		
		private int zoom(int base, int zoom) {
			return base * zoom / 100;
		}

		@Override
		protected Point getSize() {
			return new Point(width, height);
