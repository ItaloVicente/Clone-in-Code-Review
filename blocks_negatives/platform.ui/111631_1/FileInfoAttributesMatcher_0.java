		/*
		java.nio.file.FileSystem fs = java.nio.file.FileSystems.getDefault();
		java.nio.file.FileRef fileRef = fs.getPath(file);
		java.nio.file.attribute.BasicFileAttributes attributes = java.nio.file.attribute.Attributes.readBasicFileAttributes(fileRef, new java.nio.file.LinkOption[0]);
		return attributes.creationTime();
        */

		try {
			Object fs = getDefault.invoke(null);


			Method getPath = fileSystem.getMethod("getPath", String.class); //$NON-NLS-1$
			Object fileRefObj = getPath.invoke(fs, fullPath);

			Object linkOptionsEmptyArray = Array.newInstance(linkOptions, 0);
			Method readBasicFileAttributes = attributes.getMethod("readBasicFileAttributes", fileRef, //$NON-NLS-1$
					linkOptionsEmptyArray.getClass());
			Object attributesObj = readBasicFileAttributes.invoke(null, new Object[] {fileRefObj, linkOptionsEmptyArray});

			Object time = creationTime.invoke(attributesObj);

			Object result = toMillis.invoke(time);

			if (result instanceof Long) {
				return ((Long) result).longValue();
			}
		} catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e) {
