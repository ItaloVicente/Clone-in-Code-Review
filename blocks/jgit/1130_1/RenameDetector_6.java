					d.changeType = ChangeType.RENAME;
					entries.add(exactRename(d
					for (DiffEntry a : adds) {
						if (a != best) {
							if (sameType(d.oldMode
								entries.add(exactCopy(d
							} else {
								left.add(a);
							}
						}
