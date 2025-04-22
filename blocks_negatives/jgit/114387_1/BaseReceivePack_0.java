	 * If the {@link AdvertiseRefsHook} chooses to call
	 * {@link #setAdvertisedRefs(Map,Set)}, only refs set by this hook
	 * <em>and</em> selected by the {@link RefFilter} will be shown to the client.
	 * Clients may still attempt to create or update a reference not advertised by
	 * the configured {@link AdvertiseRefsHook}. These attempts should be rejected
	 * by a matching {@link PreReceiveHook}.
