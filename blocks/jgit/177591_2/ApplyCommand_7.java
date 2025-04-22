			try (OutputStream output = new FileOutputStream(f)) {
				DirCacheCheckout.getContent(repository
						new BufferLoader(buffer)
			}
		} finally {
			buffer.destroy();
