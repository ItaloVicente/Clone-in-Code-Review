	private static Iterable<Entry<String
		return new Iterable<Entry<String

			private int i = 0;

			@Override
			public Iterator<Entry<String
				Iterator<Entry<String

					@Override
					public boolean hasNext() {
						return i < refs.size();
					}

					@Override
					public Entry<String
						Ref r = refs.get(i);
						i++;
						return new Entry<String

							@Override
							public String getKey() {
								return r.getName();
							}

							@Override
							public Ref getValue() {
								return r;
							}

							@Override
							public Ref setValue(Ref value) {
								throw new UnsupportedOperationException();
							}
						};
					}
				};
				return it;
			}
		};
	}

