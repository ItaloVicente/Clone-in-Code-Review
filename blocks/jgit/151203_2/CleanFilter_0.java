			}
			aOut.close();
			AnyLongObjectId loid = aOut.getId();
			aOut = null;
			Path mediaFile = lfsUtil.getMediaFile(loid);
			if (Files.isRegularFile(mediaFile)) {
				long fsSize = Files.size(mediaFile);
				if (fsSize != size) {
					throw new CorruptMediaFile(mediaFile
				}
				FileUtils.delete(tmpFile.toFile());
