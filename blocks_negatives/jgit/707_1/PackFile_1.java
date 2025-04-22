			if (invalid)
				throw new PackInvalidException(packFile);
			fd = new RandomAccessFile(packFile, "r");
			length = fd.length();
			onOpenPack();
		} catch (IOException ioe) {
			openFail();
			throw ioe;
		} catch (RuntimeException re) {
			openFail();
			throw re;
		} catch (Error re) {
			openFail();
			throw re;
