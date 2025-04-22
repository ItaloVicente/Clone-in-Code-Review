			TemporaryBuffer bb = new TemporaryBuffer.LocalFile(dir
			try {
				tree.write(tmp
				bb.close();

				NB.encodeInt32(tmp
				NB.encodeInt32(tmp
				dos.write(tmp
				bb.writeTo(dos
			} finally {
				bb.destroy();
			}
