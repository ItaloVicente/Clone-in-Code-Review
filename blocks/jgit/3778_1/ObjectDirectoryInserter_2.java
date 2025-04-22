	private File toTemp(final int type
			final int len) throws IOException
		boolean delete = true;
		File tmp = newTempFile();
		try {
			FileOutputStream fOut = new FileOutputStream(tmp);
			try {
				OutputStream out = fOut;
				if (config.getFSyncObjectFiles())
					out = Channels.newOutputStream(fOut.getChannel());
				DeflaterOutputStream cOut = compress(out);
				writeHeader(cOut
				cOut.write(buf
				cOut.finish();
			} finally {
				if (config.getFSyncObjectFiles())
					fOut.getChannel().force(true);
				fOut.close();
			}

			delete = false;
			return tmp;
		} finally {
			if (delete)
				FileUtils.delete(tmp);
		}
	}

