
	public Color getForeground() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getForeground(this);
		}
		return null;
	}

	public Color getBackground() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IColorProvider) {
			return ((IColorProvider) provider).getBackground(this);
		}
		return null;
	}

	public Font getFont() {
		ILabelProvider provider = descriptor.getLabelProvider();
		if (provider instanceof IFontProvider) {
			return ((IFontProvider) provider).getFont(this);
		}
		return null;
	}
