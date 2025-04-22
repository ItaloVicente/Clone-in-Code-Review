	public static Transport open(URIish uri) throws NotSupportedException
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto == null) {
				protocols.remove(ref);
				continue;
			}

			if (proto.canHandle(uri
				return proto.open(uri);
		}

		throw new NotSupportedException(MessageFormat.format(JGitText.get().URINotSupported
	}

