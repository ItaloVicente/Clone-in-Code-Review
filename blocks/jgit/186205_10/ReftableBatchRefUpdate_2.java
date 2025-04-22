				if (refCache != null) {
					Iterable<Entry<String

						private int i = 0;

						@Override
						public Iterator<Entry<String
							Iterator<Entry<String

								@Override
								public boolean hasNext() {
									return i < newRefs.size() - 1;
								}

								@Override
								public Entry<String
									i++;
									Ref r = newRefs.get(i);
									return new Entry<>() {

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
					refCache.onBatchUpdated(loader);
				}
