	@Override
	public void onObjectInfo(ObjectInfoRequest req)
			throws ServiceMayNotContinueException {
		for (ProtocolV2Hook hook : hooks) {
			hook.onObjectInfo(req);
		}
	}

