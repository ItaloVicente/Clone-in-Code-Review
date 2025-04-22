	public void write(final byte[] b
		while (0 < len) {
			int capacity = buffer.length - cnt;
			if (cnt == HDR_SIZE && capacity < len) {
				PacketLineOut.formatLength(buffer
				out.write(buffer
				out.write(b
				off += capacity;
				len -= capacity;

			} else {
				if (capacity == 0)
					writeBuffer();

				int n = Math.min(len
				System.arraycopy(b
				cnt += n;
				off += n;
				len -= n;
			}
		}
