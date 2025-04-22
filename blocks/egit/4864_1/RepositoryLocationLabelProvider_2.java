		if (element instanceof CloneSourceProvider) {
			Image image = ((CloneSourceProvider) element).getImage().createImage();
			images.add(image);
			return image;
		}
		else if (element instanceof RepositoryServerInfo)
			return repoImage;
		return null;
	}

	public void dispose() {
		repoImage.dispose();
		for (Image image  : images) {
			image.dispose();
		}
