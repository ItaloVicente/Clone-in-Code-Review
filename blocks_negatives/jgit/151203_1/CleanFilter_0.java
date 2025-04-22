				aOut.close();
				AnyLongObjectId loid = aOut.getId();
				aOut = null;
				Path mediaFile = lfsUtil.getMediaFile(loid);
				if (Files.isRegularFile(mediaFile)) {
					long fsSize = Files.size(mediaFile);
					if (fsSize != size) {
						throw new CorruptMediaFile(mediaFile, size, fsSize);
					} else {
						FileUtils.delete(tmpFile.toFile());
					}
				} else {
					Path parent = mediaFile.getParent();
					if (parent != null) {
						FileUtils.mkdirs(parent.toFile(), true);
					}
					FileUtils.rename(tmpFile.toFile(), mediaFile.toFile(),
							StandardCopyOption.ATOMIC_MOVE);
