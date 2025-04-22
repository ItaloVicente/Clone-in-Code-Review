				if (parents != null)
					for (int i = 0; i < parents.size(); i++) {
						RevObject ro = rw.parseAny(parents.get(i));
						if (ro instanceof RevTag)
							parents.set(i
					}
