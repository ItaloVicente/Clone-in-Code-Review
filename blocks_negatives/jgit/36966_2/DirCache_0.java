			final TemporaryBuffer bb = new TemporaryBuffer.LocalFile();
			tree.write(tmp, bb);
			bb.close();

			NB.encodeInt32(tmp, 0, EXT_TREE);
			NB.encodeInt32(tmp, 4, (int) bb.length());
			dos.write(tmp, 0, 8);
			bb.writeTo(dos, null);
