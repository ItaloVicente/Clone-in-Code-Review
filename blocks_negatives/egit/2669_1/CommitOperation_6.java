			if (idxEntry != null) {
				projTree.addFile(repoRelativePath);
				TreeEntry newMember = projTree.findBlobMember(repoRelativePath);

				newMember.setId(idxEntry.getObjectId());
				if (newMember instanceof FileTreeEntry)
					((FileTreeEntry) newMember).setExecutable(
							(idxEntry.getModeBits() &
									FileMode.EXECUTABLE_FILE.getBits())
							== FileMode.EXECUTABLE_FILE.getBits());

				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
									+ idxEntry.getObjectId());
			}
