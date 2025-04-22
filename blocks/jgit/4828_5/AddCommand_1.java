
								if (!fileMode
										&& c != null
										&& c.getEntryFileMode() != FileMode.MISSING)
									entry.setFileMode(c.getEntryFileMode());
								else
									entry.setFileMode(f.getEntryFileMode());
