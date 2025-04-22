			if (!builder.commit()) {
				dc.unlock();
				throw new IndexWriteException();
			}
		} finally {
			objectReader.release();
