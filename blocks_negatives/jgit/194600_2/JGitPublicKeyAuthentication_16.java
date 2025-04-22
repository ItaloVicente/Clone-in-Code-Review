					if (value != null) {
						return true;
					}
					PublicKeyIdentity next = null;
					while (original.hasNext()) {
						next = original.next();
						if (!(next instanceof KeyAgentIdentity)) {
							value = next;
