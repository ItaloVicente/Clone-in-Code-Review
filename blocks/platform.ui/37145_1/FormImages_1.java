	private class SimpleSectionImageDescriptor extends AbstractImageDescriptor {
		protected int fTheight;
		protected int fMarginHeight;

		SimpleSectionImageDescriptor(Color[] colors, int realtheight, int theight, int marginHeight) {
			super(colors, realtheight);
			fTheight = theight;
			fMarginHeight = marginHeight;
		}

		public boolean equals(Object obj) {
			if (obj instanceof SimpleImageDescriptor) {
				SimpleImageDescriptor id = (SimpleImageDescriptor) obj;
				if (super.equals(obj) && id.fTheight == fTheight && id.fMarginHeight == fMarginHeight)
					return true;
			}
			return false;
		}

		public int hashCode() {
			int hash = super.hashCode();
			hash = hash * 7 + new Integer(fTheight).hashCode();
			hash = hash * 7 + new Integer(fMarginHeight).hashCode();
			return hash;
		}

		public ImageData getImageData() {
			return null;
		}

		public Image createImage(boolean returnMissingImageOnError, Device device) {
			Image image = new Image(device, 1, fLength);
			Color originalBgColor = new Color(device, fRGBs[0]);
			Color color1 = new Color(device, fRGBs[1]);
			image.setBackground(originalBgColor);
			GC gc = new GC(image);
			gc.setBackground(color1);
			gc.fillRectangle(0, fMarginHeight + 2, 1, fTheight - fMarginHeight - 3);
			gc.setBackground(originalBgColor);
			gc.fillRectangle(0, fTheight - fMarginHeight - 4, 1, 4);
			gc.dispose();
			color1.dispose();
			return image;
		}
	}

	private class SimpleSectionGradientImageDescriptor extends SimpleSectionImageDescriptor {

		SimpleSectionGradientImageDescriptor(Color originalBgColor, Color color1, Color color2, int realtheight,
				int theight,
				int marginHeight) {
			super(new Color[] { originalBgColor, color1, color2 }, realtheight, theight, marginHeight);
		}

		public Image createImage(boolean returnMissingImageOnError, Device device) {
			Image image = new Image(device, 1, fLength);
			Color originalBgColor = new Color(device, fRGBs[0]);
			Color color1 = new Color(device, fRGBs[1]);
			Color color2 = new Color(device, fRGBs[2]);
			image.setBackground(color1);
			GC gc = new GC(image);
			gc.setBackground(color1);
			gc.fillRectangle(0, 0, 1, fLength);
			gc.setForeground(color2);
			gc.setBackground(color1);
			gc.fillGradientRectangle(0, fMarginHeight + 2, 1, fTheight - 2, true);
			gc.setBackground(originalBgColor);
			gc.fillRectangle(0, fTheight - fMarginHeight - 4, 1, 4);
			gc.dispose();
			color1.dispose();
			color2.dispose();
			return image;
		}

	}

	public Image getImage(Color originalBgColor, Color color1, int realtheight, int theight, int marginHeight,
			Display display) {
		if (color1 == null || color1.isDisposed())
			return null;
		AbstractImageDescriptor desc = new SimpleSectionImageDescriptor(new Color[] { originalBgColor, color1 },
				realtheight, theight, marginHeight);
		return getGradient(desc, display);
	}

	public Image getSectionGradientImage(Color originalBgColor, Color color1, Color color2, int realtheight,
			int theight,
			int marginHeight, Display display) {
		if (color1 == null || color1.isDisposed())
			return null;
		AbstractImageDescriptor desc = new SimpleSectionGradientImageDescriptor(originalBgColor, color1, color2,
				realtheight, theight, marginHeight);
		return getGradient(desc, display);
	}

