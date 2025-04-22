						} else {
							modelChildren.add(legacySep);
						}
					} else if (OpaqueElementUtil
							.isOpaqueMenuSeparator(menuElement)) {
						MMenuSeparator legacySep = (MMenuSeparator) menuElement;
						oldSeps.remove(legacySep);
						if (modelChildren.size() > dest) {
							if (modelChildren.get(dest) != legacySep) {
								modelChildren.remove(legacySep);
								modelChildren.add(dest, legacySep);
							}
						} else {
							modelChildren.add(legacySep);
