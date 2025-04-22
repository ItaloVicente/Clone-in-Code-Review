	public void onPreAdvertiseRefs(UploadPack up)
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].onPreAdvertiseRefs(up);
	}

