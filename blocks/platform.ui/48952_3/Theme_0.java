					String thisTheme = getId();

						if (Util.equals(thisTheme, theme)) {
						if (getFontRegistry().hasValueFor(key)) {
							FontData[] data = (FontData[]) event.getNewValue();
							getFontRegistry().put(key, data);
							processDefaultsTo(key, data);
							return;
						} else if (getColorRegistry().hasValueFor(key)) {
							RGB rgb = (RGB) event.getNewValue();
							getColorRegistry().put(key, rgb);
							processDefaultsTo(key, rgb);
							return;
