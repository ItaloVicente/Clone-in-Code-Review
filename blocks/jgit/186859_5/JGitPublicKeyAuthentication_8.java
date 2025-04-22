		if (hostConfig.isIdentitiesOnly()) {
			Iterator<PublicKeyIdentity> original = keys;
			keys = new Iterator<>() {

				private PublicKeyIdentity value;

				@Override
				public boolean hasNext() {
					if (value != null) {
						return true;
					}
					PublicKeyIdentity next = null;
					while (original.hasNext()) {
						next = original.next();
						if (!(next instanceof KeyAgentIdentity)) {
							value = next;
							return true;
						}
					}
					return false;
				}

				@Override
				public PublicKeyIdentity next() {
					if (hasNext()) {
						PublicKeyIdentity result = value;
						value = null;
						return result;
					}
					throw new NoSuchElementException();
				}
			};
		}
