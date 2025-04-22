			return new AutoCloseable() {

				@Override
				public void close() throws Exception {
					gc.setForeground(oldForeground);
					gc.setBackground(oldBackground);
					gc.setAlpha(oldAlpha);
					gc.setLineStyle(oldLineStyle);
					gc.setLineWidth(oldLineWidth);
					gc.setAntialias(oldAntialias);
				}
