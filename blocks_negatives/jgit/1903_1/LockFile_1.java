		requireLock();
		try {
			final BufferedOutputStream b;
			b = new BufferedOutputStream(os, Constants.OBJECT_ID_STRING_LENGTH + 1);
			id.copyTo(b);
			b.write('\n');
			b.flush();
			fLck.release();
			b.close();
			os = null;
		} catch (IOException ioe) {
			unlock();
			throw ioe;
		} catch (RuntimeException ioe) {
			unlock();
			throw ioe;
		} catch (Error ioe) {
			unlock();
			throw ioe;
		}
