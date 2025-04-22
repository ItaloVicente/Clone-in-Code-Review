				okPressed();
			}
		});
		editorsTable.addDisposeListener(e -> {
			for (Iterator images1 = imageCache.values().iterator(); images1.hasNext();) {
				Image i1 = (Image) images1.next();
				i1.dispose();
			}
			for (Iterator images2 = disabledImageCache.values().iterator(); images2.hasNext();) {
				Image i2 = (Image) images2.next();
				i2.dispose();
			}
