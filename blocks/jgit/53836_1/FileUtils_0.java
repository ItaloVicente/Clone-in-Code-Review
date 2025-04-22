		Path nioPath = path.toPath();
		if (Files.exists(nioPath
			Files.delete(nioPath);
		}
		if (SystemReader.getInstance().isWindows()) {
			target = target.replace('/'
		}
		Path nioTarget = new File(target).toPath();
		Files.createSymbolicLink(nioPath
