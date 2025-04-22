
					if (!fileMode
							&& dcTree != null
							&& dcTree.getEntryFileMode() == FileMode.EXECUTABLE_FILE
							&& fTree.getEntryFileMode() == FileMode.REGULAR_FILE)
						dcEntry.setFileMode(dcTree.getEntryFileMode());
					else
						dcEntry.setFileMode(fTree.getEntryFileMode());
