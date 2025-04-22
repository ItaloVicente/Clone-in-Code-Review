		File tf = new File(trash, name);
		File tfp = tf.getParentFile();
		if (!tfp.exists() && !tf.getParentFile().mkdirs())
			throw new Error("Could not create directory " + tf.getParentFile());
		final OutputStreamWriter fw = new OutputStreamWriter(
				new FileOutputStream(tf), "UTF-8");
		try {
			fw.write(data);
		} finally {
			fw.close();
		}
		return tf;
