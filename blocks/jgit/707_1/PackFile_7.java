
	static final class OpenFile {
		final RandomAccessFile fd;

		OpenFile(final RandomAccessFile fd) {
			this.fd = fd;
		}
	}

	private static final class AvailableFileNode {
		final AvailableFileNode next;

		final OpenFile handle;

		AvailableFileNode(AvailableFileNode next
			this.next = next;
			this.handle = handle;
		}
	}
