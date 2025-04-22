	 * @param allRefs
	 *            explicit set of references to claim as advertised by this
	 *            ReceivePack instance. This overrides any references that may
	 *            exist in the source repository. The map is passed to the
	 *            configured {@link #getRefFilter()}. If null, assumes all refs
	 *            were advertised.
	 * @param additionalHaves
	 *            explicit set of additional haves to claim as advertised. If
	 *            null, assumes the default set of additional haves from the
	 *            repository.
