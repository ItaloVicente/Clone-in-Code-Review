			for (Iterator<?> iterator = grad.getRGBs().iterator(); iterator
					.hasNext();) {
				RGB rgb = (RGB) iterator.next();
				java.awt.Color color = new java.awt.Color(rgb.red, rgb.green,
						rgb.blue);
				colors.add(color);
