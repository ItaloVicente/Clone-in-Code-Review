        if (images != null) {
			for (Image i : images.values()) {
				i.dispose();
			}
            images = null;
        }
        super.dispose();
    }
