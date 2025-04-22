
		maskDepth = Math.max(maskDepth, baseMaskDepth = getTransparencyDepth(src));
		Image img = new Image(device, src);
		gc.drawImage(img, 0, 0);
		img.dispose();

