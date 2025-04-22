
	public static class PackfileUriConfig {
		@NonNull
		private final PacketLineOut pckOut;

		@NonNull
		private final Collection<String> protocolsSupported;

		@NonNull
		private final CachedPackUriProvider cachedPackUriProvider;

		public PackfileUriConfig(@NonNull PacketLineOut pckOut
				@NonNull Collection<String> protocolsSupported
				@NonNull CachedPackUriProvider cachedPackUriProvider) {
			this.pckOut = pckOut;
			this.protocolsSupported = protocolsSupported;
			this.cachedPackUriProvider = cachedPackUriProvider;
		}
	}
