
		byte[] inflate(long filePos
			byte[] dstbuf;
			try {
				dstbuf = new byte[len];
			} catch (OutOfMemoryError noMemory) {
			}

			byte[] srcbuf = buffer();
			Inflater inf = inflater();
			filePos += setInput(filePos
			for (int dstoff = 0;;) {
				int n = inf.inflate(dstbuf
				dstoff += n;
				if (inf.finished()) {
					return dstbuf;
				}
				if (inf.needsInput()) {
					filePos += setInput(filePos
				} else if (n == 0) {
					throw new DataFormatException();
				}
			}
		}

		private int setInput(long filePos
				throws IOException {
			if (file.getFilePointer() != filePos) {
				file.seek(filePos);
			}
			int n = file.read(buf);
			if (n < 0) {
				throw new EOFException(JGitText.get().unexpectedEofInPack);
			}
			inf.setInput(buf
			return n;
		}
	}

	private class Reader extends ObjectReader {
		private final ObjectReader ctx;

		private Reader() {
			ctx = db.newReader();
			setStreamFileThreshold(ctx.getStreamFileThreshold());
		}

		@Override
		public ObjectReader newReader() {
			return db.newReader();
		}

		@Override
		public ObjectInserter getCreatedFromInserter() {
			return PackInserter.this;
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			Collection<ObjectId> stored = ctx.resolve(id);
			if (objectList == null) {
				return stored;
			}

			Set<ObjectId> r = new HashSet<>(stored.size() + 2);
			r.addAll(stored);
			for (PackedObjectInfo obj : objectList) {
				if (id.prefixCompare(obj) == 0) {
					r.add(obj.copy());
				}
			}
			return r;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			if (objectMap == null) {
				return ctx.open(objectId
			}

			PackedObjectInfo obj = objectMap.get(objectId);
			if (obj == null) {
				return ctx.open(objectId
			}

			byte[] buf = buffer();
			RandomAccessFile f = packOut.file;
			f.seek(obj.getOffset());
			int cnt = f.read(buf
			if (cnt <= 0) {
				throw new EOFException(JGitText.get().unexpectedEofInPack);
			}

			int c = buf[0] & 0xff;
			int type = (c >> 4) & 7;
			if (type == OBJ_OFS_DELTA || type == OBJ_REF_DELTA) {
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotReadBackDelta
			}
			if (typeHint != OBJ_ANY && type != typeHint) {
				throw new IncorrectObjectTypeException(objectId.copy()
			}

			long sz = c & 0x0f;
			int ptr = 1;
			int shift = 4;
			while ((c & 0x80) != 0) {
				if (ptr >= cnt) {
					throw new EOFException(JGitText.get().unexpectedEofInPack);
				}
				c = buf[ptr++] & 0xff;
				sz += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}

			long zpos = obj.getOffset() + ptr;
			if (sz < getStreamFileThreshold()) {
				byte[] data = inflate(obj
				if (data != null) {
					return new ObjectLoader.SmallObject(type
				}
			}
			return new StreamLoader(f
		}

		private byte[] inflate(PackedObjectInfo obj
				throws IOException
			try {
				return packOut.inflate(zpos
			} catch (DataFormatException dfe) {
				CorruptObjectException coe = new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().objectAtHasBadZlibStream
								Long.valueOf(obj.getOffset())
								tmpPack.getAbsolutePath()));
				coe.initCause(dfe);
				throw coe;
			}
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return ctx.getShallowCommits();
		}

		@Override
		public void close() {
			ctx.close();
		}

		private class StreamLoader extends ObjectLoader {
			private final RandomAccessFile file;
			private final int type;
			private final long size;
			private final long pos;

			StreamLoader(RandomAccessFile file
				this.file = file;
				this.type = type;
				this.size = size;
				this.pos = pos;
			}

			@Override
			public ObjectStream openStream()
					throws MissingObjectException
				int bufsz = buffer().length;
				file.seek(pos);
				return new ObjectStream.Filter(
						type
						new BufferedInputStream(
								new InflaterInputStream(
										Channels.newInputStream(packOut.file.getChannel())
										inflater()
								bufsz));
			}

			@Override
			public int getType() {
				return type;
			}

			@Override
			public long getSize() {
				return size;
			}

			@Override
			public byte[] getCachedBytes() throws LargeObjectException {
				throw new LargeObjectException.ExceedsLimit(
						getStreamFileThreshold()
			}
		}
