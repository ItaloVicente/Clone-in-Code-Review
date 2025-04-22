			if (mode == FileMode.TREE.getBits() && entry.getFileMode().equals(FileMode.GITLINK)) {
				byte[] idBuffer = idBuffer();
				int idOffset = idOffset();
				if (entry.getObjectId().compareTo(idBuffer
					return true;
				} else if (ObjectId.zeroId().compareTo(idBuffer
					return new File(repository.getWorkTree()
				}
				return false;
			} else if (mode == FileMode.SYMLINK.getBits())
