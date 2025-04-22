		/**
		 * Decorates the given <code>decoration</code> based on the state of the
		 * given <code>repository</code>.
		 *
		 * @param decoration
		 *            the decoration to decorate
		 * @param config
		 *            the config to retrieve state from
		 * @throws IOException
		 */
		public void decorate(IDecoration decoration, GitFlowConfig config)
				throws IOException {
			decorateIcons(decoration, config);
