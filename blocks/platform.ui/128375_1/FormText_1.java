		@Override
		protected void drawCompositeImage(int width, int height) {
			ImageDataProvider imageDataProv = new ImageDataProvider() {
				@Override
				public ImageData getImageData(int zoom) {
					int zoomedWidth = zoom(width, zoom);
					int zoomedHeight = zoom(width, height);

					Image textBuffer = new Image(getDisplay(), zoomedWidth, zoomedHeight);
					Color bg = getBackground();
					Color fg = getForeground();
					if (!getEnabled()) {
						bg = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
						fg = getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
					}
					GC textGC = new GC(textBuffer/* , gc.getStyle() */);
					textGC.setForeground(fg);
					textGC.setBackground(bg);

					Font font = getFont();
					FontData fontData = font.getFontData()[0];
					int fontHeight = fontData.getHeight();
					fontData.setHeight(fontHeight * zoom / 100);
					Font f = new Font(getDisplay(), fontData);

					textGC.setFont(f);
					textGC.fillRectangle(0, 0, zoomedWidth, zoomedHeight);
					Rectangle repaintRegion = new Rectangle(0, 0, zoomedWidth, zoomedHeight);

					Paragraph[] paragraphs = model.getParagraphs();
					IHyperlinkSegment selectedLink = getSelectedLink();
					if (getDisplay().getFocusControl() != FormText.this)
						selectedLink = null;
					for (Paragraph p : paragraphs) {
						p.paint(textGC, repaintRegion, resourceTable, selectedLink, selData);
					}
					textGC.dispose();

					ImageData imageData = textBuffer.getImageData();
					textBuffer.dispose();
					f.dispose();
					return imageData;
				}
			};
			drawImage(imageDataProv, 0, 0);
		}

		private int zoom(int base, int zoom) {
			return base * zoom / 100;
		}

		@Override
		protected Point getSize() {
			return new Point(w, h);
