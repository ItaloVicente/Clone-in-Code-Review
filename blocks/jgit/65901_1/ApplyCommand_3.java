		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		}
	}

	private void applyBinary(File f
			throws IOException
        BinaryHunk binaryHunk = fh.getForwardBinaryHunk();
        switch (binaryHunk.getType())
        {
            case LITERAL_DEFLATED:

                try (InputStream inputStream = new InflaterInputStream(new GitBinaryPatchInputStream(new SkipFirstLineInputStream(new ByteArrayInputStream(binaryHunk.getBuffer()
                {
				Files.copy(inputStream
						StandardCopyOption.REPLACE_EXISTING);
                }
                break;

            case DELTA_DEFLATED:

                byte[] delta;
                try (InputStream inputStream = new InflaterInputStream(new GitBinaryPatchInputStream(new SkipFirstLineInputStream(new ByteArrayInputStream(binaryHunk.getBuffer()
                {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				copyStreams(inputStream
                    delta = outputStream.toByteArray();
                    if (delta.length != binaryHunk.getSize())
                    {
                        throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
                    }
                }
                byte[] base = Files.readAllBytes(f.toPath());
                byte[] result = BinaryDelta.apply(base
			Files.write(f.toPath()
					StandardOpenOption.TRUNCATE_EXISTING);
                break;
        }
