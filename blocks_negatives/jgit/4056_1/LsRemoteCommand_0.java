					for (Ref r : refs) {
						boolean found = refSpecs.isEmpty();
						for (RefSpec rs : refSpecs) {
							if (rs.matchSource(r)) {
								found = true;
								break;
							}
						}
						if (found) {
