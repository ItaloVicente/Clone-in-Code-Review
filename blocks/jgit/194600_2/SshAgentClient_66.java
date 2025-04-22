	@Override
	public void addIdentity(KeyPair key
			SshAgentKeyConstraint... constraints) throws IOException {
		boolean debugging = LOG.isDebugEnabled();
		if (!open(debugging)) {
			return;
		}

		byte cmd = (constraints != null && constraints.length > 0)
				? SshAgentConstants.SSH2_AGENTC_ADD_ID_CONSTRAINED
				: SshAgentConstants.SSH2_AGENTC_ADD_IDENTITY;
		byte[] message = null;
		ByteArrayBuffer msg = new ByteArrayBuffer();
		try {
			msg.putInt(0);
			msg.putByte(cmd);
			String keyType = KeyUtils.getKeyType(key);
			if (KeyPairProvider.SSH_ED25519.equals(keyType)) {
				putEd25519Key(msg
			} else {
				msg.putKeyPair(key);
			}
			if (constraints != null) {
				for (SshAgentKeyConstraint constraint : constraints) {
					constraint.put(msg);
				}
			}
			if (debugging) {
				LOG.debug(
						"addIdentity: adding {} key {} to SSH agent; comment {}"
						keyType
						comment);
			}
			message = msg.getCompactData();
		} finally {
			msg.clear();
		}
		Buffer reply;
		try {
			reply = rpc(cmd
		} finally {
			Arrays.fill(message
		}
		int replyLength = reply.available();
		if (replyLength != 1) {
			throw new SshException(MessageFormat.format(
					SshdText.get().sshAgentReplyUnexpected
					MessageFormat.format(
							SshdText.get().sshAgentPayloadLengthError
							Integer.valueOf(1)

		}
		cmd = reply.getByte();
		if (cmd != SshAgentConstants.SSH_AGENT_SUCCESS) {
			throw new SshException(
					MessageFormat.format(SshdText.get().sshAgentReplyUnexpected
							SshAgentConstants.getCommandMessageName(cmd)));
		}
	}

	private static void putEd25519Key(Buffer msg
			throws IOException {
		Buffer tmp = new ByteArrayBuffer(36);
		tmp.putRawPublicKeyBytes(key.getPublic());
		byte[] publicBytes = tmp.getBytes();
		msg.putString(KeyPairProvider.SSH_ED25519);
		msg.putBytes(publicBytes);
		PrivateKey pk = key.getPrivate();
		String format = pk.getFormat();
			throw new IOException(MessageFormat
					.format(SshdText.get().sshAgentEdDSAFormatError
		}
		byte[] privateBytes = null;
		byte[] encoded = pk.getEncoded();
		try {
			privateBytes = asn1Parse(encoded
			byte[] combined = Arrays.copyOf(privateBytes
			Arrays.fill(privateBytes
			privateBytes = combined;
			System.arraycopy(publicBytes
			msg.putBytes(privateBytes);
		} finally {
			if (privateBytes != null) {
				Arrays.fill(privateBytes
			}
			Arrays.fill(encoded
		}
	}

	private static byte[] asn1Parse(byte[] encoded
		byte[] privateKey = null;
		try (DERParser byteParser = new DERParser(encoded);
				DERParser oneAsymmetricKey = byteParser.readObject()
						.createParser()) {
			privateKey = oneAsymmetricKey.readObject().getValue();
			return Arrays.copyOfRange(privateKey
					privateKey.length - n
		} finally {
			if (privateKey != null) {
				Arrays.fill(privateKey
			}
		}
	}

	private static PublicKey readKey(Buffer buffer) throws BufferException {
		int endOfBuffer = buffer.wpos();
		int keyLength = buffer.getInt();
		int afterKey = buffer.rpos() + keyLength;
		if (keyLength <= 0 || afterKey > endOfBuffer) {
			throw new BufferException(
					MessageFormat.format(SshdText.get().sshAgentWrongKeyLength
							Integer.toString(keyLength)
							Integer.toString(buffer.rpos())
							Integer.toString(endOfBuffer)));
		}
		buffer.wpos(afterKey);
		try {
			return buffer.getRawPublicKey(BufferPublicKeyParser.DEFAULT);
		} catch (Exception e) {
			LOG.warn(SshdText.get().sshAgentUnknownKey
			return null;
		} finally {
			buffer.wpos(endOfBuffer);
			buffer.rpos(afterKey);
		}
	}

