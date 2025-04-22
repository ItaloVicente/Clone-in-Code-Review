					@Override
					public PushCertificate next() {
						hasNext();
						PushCertificate n = next;
						if (n == null) {
							throw new NoSuchElementException();
						}
						next = null;
						return n;
					}
