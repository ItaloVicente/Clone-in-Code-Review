	/**
	 * Special mode: the URI is preset by the wizard
	 *
	 * @param presetUri remote URI
	 */
	public RepositorySelectionPage(String presetUri) {
		this(true, null);
		this.presetUri = presetUri;
	}
