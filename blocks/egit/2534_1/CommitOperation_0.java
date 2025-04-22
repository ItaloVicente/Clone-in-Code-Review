				if (newMember instanceof FileTreeEntry) {
					((FileTreeEntry) newMember).setExecutable(
							(idxEntry.getModeBits() &
									FileMode.EXECUTABLE_FILE.getBits())
							== FileMode.EXECUTABLE_FILE.getBits());
				}

