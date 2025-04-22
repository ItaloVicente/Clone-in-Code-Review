		MessageDigest md = initCopyPack(out
		long p;
		if (cache.copyThroughCache(length))
			p = copyPackThroughCache(out
		else
			p = copyPackBypassCache(out
		verifyPackChecksum(out
	}

	private MessageDigest initCopyPack(PackOutputStream out
			DfsReader ctx) throws IOException {
		if (length == -1)
			ctx.pin(this
		if (validate) {
			MessageDigest md = Constants.newMessageDigest();
			byte[] buf = out.getCopyBuffer();
			if (ctx.copy(this
				ctx.unpin();
				setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated
			}
			md.update(buf
			ctx.unpin();
			return md;
		}
		ctx.unpin();
		return null;
	}

	private long copyPackThroughCache(PackOutputStream out
			MessageDigest md) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this
			int ptr = (int) (position - b.start);
			int n = (int) Math.min(b.size() - ptr
			b.write(out
			position += n;
			remaining -= n;
		}
		return position;
	}

	private long copyPackBypassCache(PackOutputStream out
			MessageDigest md) throws IOException {
		try (ReadableChannel rc = ctx.db.openFile(packDesc
			ByteBuffer buf = newCopyBuffer(out
			long position = 12;
			long remaining = length - (12 + 20);
			while (0 < remaining) {
				DfsBlock b = cache.get(key
				if (b != null) {
					int ptr = (int) (position - b.start);
					int n = (int) Math.min(b.size() - ptr
					b.write(out
					position += n;
					remaining -= n;
					rc.position(position);
					continue;
				}

				buf.position(0);
				int n = read(rc
				if (n <= 0) {
					setInvalid();
					throw new IOException(MessageFormat.format(
							JGitText.get().packfileIsTruncated
				}
				if (n > remaining)
					n = (int) remaining;
				out.write(buf.array()
				if (md != null)
					md.update(buf.array()
				position += n;
				remaining -= n;
			}
			return position;
		}
	}

	private ByteBuffer newCopyBuffer(PackOutputStream out
			DfsReader ctx) {
		int bs = blockSize(rc);
		int bufferSize = ctx.getOptions().getStreamPackBufferSize();
		if (bufferSize > bs)
			bs = (bufferSize / bs) * bs;

		byte[] copyBuf = out.getCopyBuffer();
		if (bs > copyBuf.length)
			copyBuf = new byte[bs];
		return ByteBuffer.wrap(copyBuf
	}

	private void verifyPackChecksum(PackOutputStream out
			MessageDigest md
		if (md != null) {
			byte[] buf = out.getCopyBuffer();
			byte[] actHash = md.digest();
			if (ctx.copy(this
				setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated
			}
			if (!Arrays.equals(actHash
				setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected
						getPackName()));
			}
		}
