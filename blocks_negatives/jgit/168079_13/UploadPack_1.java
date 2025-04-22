	 * @param allRefs
	 *            explicit set of references to claim as advertised by this
	 *            UploadPack instance. This overrides any references that may
	 *            exist in the source repository. The map is passed to the
	 *            configured {@link #getRefFilter()}. If null, assumes all refs
	 *            were advertised.
