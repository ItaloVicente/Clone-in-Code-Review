			assertTrue(
					Arrays.equals(hoverDescriptor.getImageData(100).data, item.getHotImage().getImageData(100).data));
			assertTrue(Arrays.equals(disabledDescriptor.getImageData(100).data,
					item.getDisabledImage().getImageData(100).data));
			ImageData imageData = item.getImage().getImageData(100);
			for (int x = 0; x < imageData.width; x++) {
				for (int y = 0; y < imageData.height; y++) {
					if (imageData.getAlpha(x, y) == 255) {
						int rgb = imageData.getPixel(x, y);
						int r = rgb & 0xFF;
						int g = (rgb >> 8) & 0xFF;
						int b = (rgb >> 16) & 0xFF;
						assertEquals(r, g);
						assertEquals(g, b);
					}
				}

			}
