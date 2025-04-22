
	private static class HostKeyHelper extends KnownHostsServerKeyVerifier {

		public HostKeyHelper() {
			super((c, r, s) -> false, new File(".").toPath()); //$NON-NLS-1$
		}
