				File file = asFile();
				if (file == null)
					throw new IOException(MessageFormat.format(
							CoreText.ContainerTreeIterator_DeletedFile, rsrc));
				return new ByteArrayInputStream(FS.DETECTED.readSymLink(file)
						.getBytes(Constants.CHARACTER_ENCODING));
