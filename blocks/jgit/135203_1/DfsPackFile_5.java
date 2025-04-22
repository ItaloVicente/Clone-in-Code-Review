		} catch (EOFException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().shortReadOfIndex
							desc.getFileName(INDEX))
					e);
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().cannotReadIndex
		}
	}

	private PackIndex idx(DfsReader ctx) throws IOException {
		CachedIndices indices = cachedIndices;
		if (indices != null) {
			return indices.index;
