			for (Object rgbObj : grad.getRGBs()) {
				if (rgbObj instanceof RGBA) {
					RGBA rgba = (RGBA) rgbObj;
					java.awt.Color color = new java.awt.Color(rgba.rgb.red, rgba.rgb.green, rgba.rgb.blue, rgba.alpha);
					colors.add(color);
				} else if (rgbObj instanceof RGB) {
					RGB rgb = (RGB) rgbObj;
					java.awt.Color color = new java.awt.Color(rgb.red, rgb.green, rgb.blue);
					colors.add(color);
				}
