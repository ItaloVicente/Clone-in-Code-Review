		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				newContent.getBytes(prj.getDefaultCharset()));
		if (!file.exists())
			file.create(inputStream, 0, null);
		else
			file.setContents(inputStream, 0, null);
