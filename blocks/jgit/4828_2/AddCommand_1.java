
								if (c != null
										&& f.useIndexMode(c.getEntryFileMode()
												fs))
									entry.setFileMode(c.getEntryFileMode());
								else
									entry.setFileMode(f.getEntryFileMode());
