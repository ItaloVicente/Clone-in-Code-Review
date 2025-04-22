		@Override
		public void advertiseId(AnyObjectId id
				throws IOException {
			id.copyTo(binArr
			binArr[OBJECT_ID_STRING_LENGTH] = ' ';
			binBuf.position(OBJECT_ID_STRING_LENGTH + 1);
			append(refName);
			if (first) {
				first = false;
				if (!capablities.isEmpty()) {
					append('\0');
					for (String cap : capablities) {
						append(' ');
						append(cap);
					}
				}
			}
			append('\n');
			pckOut.writePacket(binArr
		}

		private void append(String str) throws CharacterCodingException {
			int n = str.length();
			if (n > chArr.length) {
				chArr = new char[n + 256];
				chBuf = CharBuffer.wrap(chArr);
			}
			str.getChars(0
			chBuf.position(0).limit(n);
			utf8.reset();
			for (;;) {
				CoderResult cr = utf8.encode(chBuf
				if (cr.isOverflow()) {
					grow();
				} else if (cr.isUnderflow()) {
					break;
				} else {
					cr.throwException();
				}
			}
		}

		private void append(int b) {
			if (!binBuf.hasRemaining()) {
				grow();
			}
			binBuf.put((byte) b);
		}

		private void grow() {
			int cnt = binBuf.position();
			byte[] tmp = new byte[binArr.length << 1];
			System.arraycopy(binArr
			binArr = tmp;
			binBuf = ByteBuffer.wrap(binArr);
			binBuf.position(cnt);
		}

