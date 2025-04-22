		this.checkCancelAt = BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
	}

	/** {@inheritDoc} */
	@Override
	public final void write(int b) throws IOException {
		count++;
		out.write(b);
		md.update((byte) b);
	}

	/** {@inheritDoc} */
	@Override
	public final void write(byte[] b, int off, int len)
			throws IOException {
		while (0 < len) {
			final int n = Math.min(len, BYTES_TO_WRITE_BEFORE_CANCEL_CHECK);
			count += n;

			if (checkCancelAt <= count) {
				if (writeMonitor.isCancelled()) {
					throw new IOException(
							JGitText.get().packingCancelledDuringObjectsWriting);
				}
				checkCancelAt = count + BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
			}

			out.write(b, off, n);
			md.update(b, off, n);

			off += n;
			len -= n;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void flush() throws IOException {
		out.flush();
