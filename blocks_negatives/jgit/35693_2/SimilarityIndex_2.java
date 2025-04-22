			ObjectStream in = obj.openStream();
			try {
				setFileSize(in.getSize());
				hash(in, fileSize);
			} finally {
				in.close();
			}
