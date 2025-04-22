		super.dispose();
		for (Iterator e = imagesToDispose.iterator(); e.hasNext();) {
			((Image) e.next()).dispose();
		}
		imagesToDispose.clear();
	}
