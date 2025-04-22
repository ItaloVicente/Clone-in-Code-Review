		private Image silhouette;

		public ContentTypesLabelProvider(Color color) {
			this.silhouette = createImage(getFont(), "\uD83D\uDC64", color); //$NON-NLS-1$
		}

		private Image createImage(Font font, String s, Color background) {
			TextLayout textLayout = new TextLayout(font.getDevice());
			textLayout.setText(s);
			textLayout.setFont(font);
			Rectangle bounds = textLayout.getBounds();
			Image image = new Image(font.getDevice(), bounds.width, bounds.height);
			GC gc = new GC(image);
			gc.setBackground(background);
			textLayout.draw(gc, 0, 0);
			textLayout.dispose();
			return image;
		}

