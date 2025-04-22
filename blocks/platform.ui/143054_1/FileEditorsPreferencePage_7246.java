		super.dispose();
		if (imagesToDispose != null) {
			for (Iterator e = imagesToDispose.iterator(); e.hasNext();) {
				((Image) e.next()).dispose();
			}
			imagesToDispose = null;
		}
		if (editorsToImages != null) {
			for (Iterator e = editorsToImages.values().iterator(); e.hasNext();) {
				((Image) e.next()).dispose();
			}
			editorsToImages = null;
		}
	}

	@Override
