public abstract class GpgSigner {

	private static GpgSigner defaultSigner = new BouncyCastleGpgSigner();

	/**
	 * Get the default signer, or <code>null</code>.
	 *
	 * @return the default signer, or <code>null</code>.
	 */
	public static GpgSigner getDefault() {
		return defaultSigner;
	}

	/**
	 * Set the default signer.
	 *
	 * @param signer
	 *            the new default signer, may be <code>null</code> to select no
	 *            default.
	 */
	public static void setDefault(GpgSigner signer) {
		GpgSigner.defaultSigner = signer;
	}
