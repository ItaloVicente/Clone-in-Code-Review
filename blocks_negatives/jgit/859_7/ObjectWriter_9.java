			byte[] header;
			int n;

			header = Constants.encodedTypeString(type);
			md.update(header);
			if (deflateStream != null)
				deflateStream.write(header);

			md.update((byte) ' ');
			if (deflateStream != null)
				deflateStream.write((byte) ' ');

			header = Constants.encodeASCII(len);
			md.update(header);
			if (deflateStream != null)
				deflateStream.write(header);

			md.update((byte) 0);
			if (deflateStream != null)
				deflateStream.write((byte) 0);

			while (len > 0
					&& (n = is.read(buf, 0, (int) Math.min(len, buf.length))) > 0) {
				md.update(buf, 0, n);
				if (deflateStream != null)
					deflateStream.write(buf, 0, n);
				len -= n;
			}

			if (len != 0)
				throw new IOException("Input did not match supplied length. "
						+ len + " bytes are missing.");

			if (deflateStream != null ) {
				deflateStream.close();
				if (t != null)
					t.setReadOnly();
			}

			id = ObjectId.fromRaw(md.digest());
