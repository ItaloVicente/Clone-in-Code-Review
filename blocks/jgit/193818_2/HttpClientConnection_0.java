
	@Override
	public void close() throws Exception {
		if (closeableResp != null) {
			closeableResp.close();
			closeableResp = null;
			resp = null;
		}
	}
