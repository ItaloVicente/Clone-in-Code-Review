			if (mode == FileMode.TREE.getBits()
					&& entry.getFileMode().equals(FileMode.GITLINK)) {
				byte[] idBuffer = idBuffer();
				int idOffset = idOffset();
				if (entry.getObjectId().compareTo(idBuffer
					return true;
				} else if (ObjectId.zeroId().compareTo(idBuffer
						idOffset) == 0) {
					return new File(repository.getWorkTree()
							entry.getPathString()).list().length > 0;
				}
				return false;
			} else if (mode == FileMode.SYMLINK.getBits())
