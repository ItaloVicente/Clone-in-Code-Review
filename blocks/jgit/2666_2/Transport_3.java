	public static Transport open(Repository local
			throws NotSupportedException
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto == null) {
				protocols.remove(ref);
				continue;
			}
