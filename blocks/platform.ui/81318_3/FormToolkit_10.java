		composite.addListener(SWT.Paint, e -> {
			if (composite.isDisposed())
				return;
			Rectangle bounds = composite.getBounds();
			GC gc = e.gc;
			gc.setForeground(colors.getColor(IFormColors.SEPARATOR));
			if (colors.getBackground() != null)
				gc.setBackground(colors.getBackground());
			gc.fillGradientRectangle(0, 0, bounds.width, bounds.height, false);
