
								if (!fileMode
										&& c != null
										&& c.getEntryFileMode() == FileMode.EXECUTABLE_FILE
										&& f.getEntryFileMode() == FileMode.REGULAR_FILE)
									entry.setFileMode(c.getEntryFileMode());
								else
									entry.setFileMode(f.getEntryFileMode());
