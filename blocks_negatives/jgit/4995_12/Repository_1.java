		if (msg != null) {
			FileOutputStream fos = new FileOutputStream(mergeMsgFile);
			try {
				fos.write(msg.getBytes(Constants.CHARACTER_ENCODING));
			} finally {
				fos.close();
			}
		} else {
			FileUtils.delete(mergeMsgFile, FileUtils.SKIP_MISSING);
		}
