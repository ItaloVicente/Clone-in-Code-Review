					Iterable<Entry<String, Ref>> loader = new Iterable<>() {

						private int i = 0;

						@Override
						public Iterator<Entry<String, Ref>> iterator() {
							Iterator<Entry<String, Ref>> it = new Iterator<>() {

								@Override
								public boolean hasNext() {
									return i < newRefs.size() - 1;
								}

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
