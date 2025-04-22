							@Override
							public Entry<String, Ref> next() {
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
