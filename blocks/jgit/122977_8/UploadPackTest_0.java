		return uploadPackV2(null
	}

	private static class TestV2Hook implements ProtocolV2Hook {
		private CapabilitiesV2Request capabilitiesRequest;

		private LsRefsV2Request lsRefsRequest;

		@Override
		public void onCapabilities(CapabilitiesV2Request req) {
			capabilitiesRequest = req;
		}

		@Override
		public void onLsRefs(LsRefsV2Request req) {
			lsRefsRequest = req;
		}
