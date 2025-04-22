	/**
	 * Set the GPG signature of this commit.
	 * <p>
	 * Note, the signature set here will change the payload of the commit, i.e.
	 * the output of {@link #build()} will include the signature. Thus, the
	 * typical flow will be:
	 * <ol>
	 * <li>call {@link #build()} without a signature set to obtain payload</li>
	 * <li>create {@link GpgSignature} from payload</li>
	 * <li>set {@link GpgSignature}</li>
	 * </ol>
	 * </p>
	 *
	 * @param newSignature
	 *            the signature to set or <code>null</code> to unset
	 * @since 5.3
	 */
	public void setGpgSignature(GpgSignature newSignature) {
		gpgSignature = newSignature;
	}

	/**
	 * Get the GPG signature of this commit.
	 *
	 * @return the GPG signature of this commit, maybe <code>null</code> if the
	 *         commit is not to be signed
	 * @since 5.3
	 */
	public GpgSignature getGpgSignature() {
		return gpgSignature;
	}

