					r = avail.get(p);
				if (r != null) {
					r = new Ref(loose(r), name, r.getObjectId(), r
							.getPeeledObjectId(), true);
					avail.put(name, r);
				}
