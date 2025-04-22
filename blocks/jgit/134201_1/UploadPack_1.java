	private void invokeAdvertiseRefsHook(UploadPack up)
			throws ServiceMayNotContinueException {
		advertiseRefsHook.advertiseRefs(up);
		advertiseRefsHookCalled = true;
	}

