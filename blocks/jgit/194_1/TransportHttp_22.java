						final String target = line.substring(5);
						Ref r = refs.get(target);
						if (r == null)
							r = new ObjectIdRef(Ref.Storage.NEW
						r = new SymbolicRef(r
						refs.put(r.getName()
