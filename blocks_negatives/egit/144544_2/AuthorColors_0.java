		RGB rgb = colors.get(author);
		if (rgb == null) {
			rgb = COMMITTER_RGBs[count++ % COMMITTER_RGBs.length];
			colors.put(author, rgb);
		}
		return rgb;
