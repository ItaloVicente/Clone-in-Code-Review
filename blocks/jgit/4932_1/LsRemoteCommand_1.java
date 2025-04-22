				refs = fc.getRefs();
				if (refSpecs.isEmpty())
					for (Ref r : refs)
						refmap.put(r.getName()
				else
					for (Ref r : refs)
						for (RefSpec rs : refSpecs)
							if (rs.matchSource(r)) {
								refmap.put(r.getName()
								break;
							}
