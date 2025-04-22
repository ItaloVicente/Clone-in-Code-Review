	 * If the {@link org.eclipse.jgit.transport.AdvertiseRefsHook} chooses to
	 * call {@link #setAdvertisedRefs(Map,Set)}, only refs set by this hook
	 * <em>and</em> selected by the {@link org.eclipse.jgit.transport.RefFilter}
	 * will be shown to the client. Clients may still attempt to create or
	 * update a reference not advertised by the configured
	 * {@link org.eclipse.jgit.transport.AdvertiseRefsHook}. These attempts
