	@Override
	protected boolean readIdentification(Buffer buffer) throws IOException {
		try {
			return super.readIdentification(buffer);
		} catch (IllegalStateException e) {
			throw new IOException(e.getLocalizedMessage()
		}
	}

	@Override
	protected List<String> doReadIdentification(Buffer buffer
		if (server) {
			throw new IllegalStateException(
		}
		int maxIdentSize = PropertyResolverUtils.getIntProperty(this
				FactoryManager.MAX_IDENTIFICATION_SIZE
				DEFAULT_MAX_IDENTIFICATION_SIZE);
		int current = buffer.rpos();
		int end = current + buffer.available();
		if (current >= end) {
			return null;
		}
		byte[] raw = buffer.array();
		List<String> ident = new ArrayList<>();
		int start = current;
		boolean hasNul = false;
		for (int i = current; i < end; i++) {
			switch (raw[i]) {
			case 0:
				hasNul = true;
				break;
			case '\n':
				int eol = 1;
				if (i > start && raw[i - 1] == '\r') {
					eol++;
				}
				String line = new String(raw
						StandardCharsets.UTF_8);
				start = i + 1;
				if (log.isDebugEnabled()) {
					log.debug(format("doReadIdentification({0}) line: "
							escapeControls(line));
				}
				ident.add(line);
					if (hasNul) {
						throw new IllegalStateException(
								format(SshdText.get().serverIdWithNul
										escapeControls(line)));
					}
					if (line.length() + eol > 255) {
						throw new IllegalStateException(
								format(SshdText.get().serverIdTooLong
										escapeControls(line)));
					}
					buffer.rpos(start);
					return ident;
				}
				hasNul = false;
				break;
			default:
				break;
			}
			if (i - current + 1 >= maxIdentSize) {
				String msg = format(SshdText.get().serverIdNotReceived
						Integer.toString(maxIdentSize));
				if (log.isDebugEnabled()) {
					log.debug(msg);
					log.debug(buffer.toHex());
				}
				throw new IllegalStateException(msg);
			}
		}
		return null;
	}

	private static String escapeControls(String s) {
		StringBuilder b = new StringBuilder();
		int l = s.length();
		for (int i = 0; i < l; i++) {
			char ch = s.charAt(i);
			if (Character.isISOControl(ch)) {
						.append(Integer.toHexString(ch));
			} else {
				b.append(ch);
			}
		}
		return b.toString();
	}

