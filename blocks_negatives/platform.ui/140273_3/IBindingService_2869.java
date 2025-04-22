	 * Writes the given active scheme and bindings to the preference store. Only
	 * the bindings that are of the <code>Binding.USER</code> type will be
	 * written; the others will be ignored. This should only be used by
	 * applications trying to persist user preferences. If you are trying to
	 * change the active scheme as an RCP application, then you should be using
	 * the <code>plugin_customization.ini</code> file. If you are trying to
	 * switch between groups of bindings dynamically, you should be using
	 * contexts.
