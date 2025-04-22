		while (0 < len) {
			final int n = Math.min(len
			count += n;

			if (checkCancelAt <= count) {
				if (writeMonitor.isCancelled()) {
					throw new IOException(
							JGitText.get().packingCancelledDuringObjectsWriting);
				}
				checkCancelAt = count + BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
			}

			out.write(b
			crc.update(b
			md.update(b

			off += n;
			len -= n;
		}
