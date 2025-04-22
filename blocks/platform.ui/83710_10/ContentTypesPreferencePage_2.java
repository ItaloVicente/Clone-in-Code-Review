		private Image silhouette;

		public ContentTypesLabelProvider() {
			this.silhouette = createImage(getFont(), "\uD83D\uDC64"); //$NON-NLS-1$
		}

		private Image createImage(Font font, String s) {
			TextLayout textLayout = new TextLayout(font.getDevice());
			textLayout.setText(s);
			textLayout.setFont(font);
			Rectangle bounds = textLayout.getBounds();
			PaletteData palette = new PaletteData(0xFF, 0xFF00, 0xFF0000);
			ImageData imageData = new ImageData(bounds.width, bounds.height, 32, palette);
			imageData.transparentPixel = palette
					.getPixel(font.getDevice().getSystemColor(SWT.COLOR_TRANSPARENT).getRGB());
			for (int column = 0; column < imageData.width; column++) {
				for (int line = 0; line < imageData.height; line++) {
					imageData.setPixel(column, line, imageData.transparentPixel);
				}
			}
			Image image = new Image(font.getDevice(), imageData);
			GC gc = new GC(image);
			textLayout.draw(gc, 0, 0);
			return image;
		}

