
	private final class ViewMenuImageDescriptor extends CompositeImageDescriptor {
		@Override
		protected void drawCompositeImage(int width, int height) {
			ImageDataProvider imageProvider = zoom -> {
				int zoomedWidth = zoom(width, zoom);
				int zoomedHeight = zoom(height, zoom);

				Point topLeft = new Point(4, 3);
				Point topRight = new Point(13, 3);
				Point bottomRight = new Point(9, 7);
				Point bottomLeft = new Point(8, 7);
				Stream<Point> points = Stream.of(topLeft, topRight, bottomRight, bottomLeft);
				int[] shapeArray = zoom(points, zoom);

				Display display = Display.getCurrent();
				Image viewMenu = new Image(display, zoomedWidth, zoomedHeight);
				GC gc = new GC(viewMenu);
				gc.setLineWidth(1 * zoom / 100);
				gc.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));

				gc.fillPolygon(shapeArray);
				gc.drawPolygon(shapeArray);

				ImageData data = viewMenu.getImageData();
				data.transparentPixel = data.getPixel(0, 0);

				Image viewMenuMask = new Image(display, zoomedWidth, zoomedHeight);
				GC maskgc = new GC(viewMenuMask);
				maskgc.setLineWidth(zoom(1, zoom));
				maskgc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
				maskgc.fillRectangle(0, 0, zoomedWidth, zoomedHeight);

				maskgc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
				maskgc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
				maskgc.fillPolygon(shapeArray);
				maskgc.drawPolygon(shapeArray);
				gc.dispose();
				maskgc.dispose();

				Image finalImage = new Image(display, viewMenu.getImageData(), viewMenuMask.getImageData());
				ImageData imgData = finalImage.getImageData();
				viewMenu.dispose();
				viewMenuMask.dispose();
				finalImage.dispose();
				return imgData;
			};
			drawImage(imageProvider, 0, 0);
		}

		@Override
		protected Point getSize() {
			return new Point(16, 16);
		}

		private int zoom(int base, int zoom) {
			return base * zoom / 100;
		}

		private int[] zoom(Stream<Point> base, int zoom) {
			return base.flatMap(p -> Stream.of(p.x, p.y)).mapToInt(i -> zoom(i, zoom)).toArray();
		}
	}

