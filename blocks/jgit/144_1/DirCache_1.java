    public static DirCache lock(final File indexLocation
            throws CorruptObjectException
        final DirCache c = new DirCache(indexLocation
        if (!c.lock())
            throw new IOException("Cannot lock " + indexLocation);

        try {
            c.read();
        } catch (IOException e) {
            c.unlock();
            throw e;
        } catch (RuntimeException e) {
			c.unlock();
			throw e;
		} catch (Error e) {
			c.unlock();
			throw e;
		}

		return c;
	}

