			for (Object rgbObj : grad.getRGBs()) {
				if (rgbObj instanceof RGBA) {
					RGBA rgba = (RGBA) rgbObj;
					Color color = new Color(control.getDisplay(), rgba);
					colors.add(color);
				} else if (rgbObj instanceof RGB) {
					RGB rgb = (RGB) rgbObj;
					Color color = new Color(control.getDisplay(), rgb);
					colors.add(color);
				}
