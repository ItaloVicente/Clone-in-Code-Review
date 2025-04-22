			if (zoom == 100) {
				cached = baseImage.getImageData();
			} else {
				cached = baseImage.getImageDataAtCurrentZoom();
				Rectangle bounds = baseImage.getBounds();
				if (bounds.width != scaleDown(cached.width, zoom) && bounds.height == scaleDown(cached.height, zoom)) {
					zoom = 0;
					cached = null;
				}
			}
