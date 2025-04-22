		if (!active && !onBottom) {
			RGB blendColor = gc.getDevice()
					.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW).getRGB();
			RGB topGradient = blend(blendColor, parent.getParent()
					.getBackground().getRGB(), 40);
			gradientTop = new Color(gc.getDevice(), topGradient);
