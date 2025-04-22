	public void sendAdvertisedRefs(final RefAdvertiser adv)
			throws IOException, ServiceMayNotContinueException {
		if (advertiseError != null) {
			return;
		}

		try {
			advertiseRefsHook.advertiseRefs(this);
		} catch (ServiceMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				fail.setOutput();
			}
			throw fail;
		}

		adv.init(db);
		adv.advertiseCapability(CAPABILITY_SIDE_BAND_64K);
		adv.advertiseCapability(CAPABILITY_DELETE_REFS);
		adv.advertiseCapability(CAPABILITY_REPORT_STATUS);
		if (allowQuiet)
			adv.advertiseCapability(CAPABILITY_QUIET);
		String nonce = getPushCertificateParser().getAdvertiseNonce();
		if (nonce != null) {
			adv.advertiseCapability(nonce);
		}
		if (db.getRefDatabase().performsAtomicTransactions())
			adv.advertiseCapability(CAPABILITY_ATOMIC);
		if (allowOfsDelta)
			adv.advertiseCapability(CAPABILITY_OFS_DELTA);
		if (allowPushOptions) {
			adv.advertiseCapability(CAPABILITY_PUSH_OPTIONS);
			pushOptions = new ArrayList<>();
		}
		adv.advertiseCapability(OPTION_AGENT, UserAgent.get());
		adv.send(getAdvertisedOrDefaultRefs());
		for (ObjectId obj : advertisedHaves)
			adv.advertiseHave(obj);
		if (adv.isEmpty())
			adv.advertiseId(ObjectId.zeroId(), "capabilities^{}"); //$NON-NLS-1$
		adv.end();
	}

	/**
	 * Receive a list of commands from the input.
	 *
	 * @throws IOException
	 */
	protected void recvCommands() throws IOException {
		PushCertificateParser certParser = getPushCertificateParser();
		boolean firstPkt = true;
		try {
			for (;;) {
				String line;
				try {
					line = pckIn.readString();
				} catch (EOFException eof) {
					if (commands.isEmpty())
						return;
					throw eof;
				}
				if (line == PacketLineIn.END) {
					break;
				}

					parseShallow(line.substring(8, 48));
					continue;
				}

				if (firstPkt) {
					firstPkt = false;
					FirstLine firstLine = new FirstLine(line);
					enabledCapabilities = firstLine.getCapabilities();
					line = firstLine.getLine();
					enableCapabilities();

					if (line.equals(GitProtocolConstants.OPTION_PUSH_CERT)) {
						certParser.receiveHeader(pckIn, !isBiDirectionalPipe());
						continue;
					}
				}

				if (line.equals(PushCertificateParser.BEGIN_SIGNATURE)) {
					certParser.receiveSignature(pckIn);
					continue;
				}

				ReceiveCommand cmd = parseCommand(line);
				if (cmd.getRefName().equals(Constants.HEAD)) {
					cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
				} else {
					cmd.setRef(refs.get(cmd.getRefName()));
				}
				commands.add(cmd);
				if (certParser.enabled()) {
					certParser.addCommand(cmd);
				}
			}
			pushCert = certParser.build();
		} catch (PackProtocolException e) {
			if (sideBand) {
				try {
					pckIn.discardUntilEnd();
				} catch (IOException e2) {
				}
			}
			fatalError(e.getMessage());
			throw e;
		}
	}

	private void parseShallow(String idStr) throws PackProtocolException {
		ObjectId id;
		try {
			id = ObjectId.fromString(idStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(e.getMessage(), e);
		}
		clientShallowCommits.add(id);
	}

	static ReceiveCommand parseCommand(String line) throws PackProtocolException {
          if (line == null || line.length() < 83) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		String oldStr = line.substring(0, 40);
		String newStr = line.substring(41, 81);
		ObjectId oldId, newId;
		try {
			oldId = ObjectId.fromString(oldStr);
			newId = ObjectId.fromString(newStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef, e);
		}
		String name = line.substring(82);
		if (!Repository.isValidRefName(name)) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		return new ReceiveCommand(oldId, newId, name);
	}

	/** Enable capabilities based on a previously read capabilities line. */
	protected void enableCapabilities() {
		sideBand = isCapabilityEnabled(CAPABILITY_SIDE_BAND_64K);
		quiet = allowQuiet && isCapabilityEnabled(CAPABILITY_QUIET);
		usePushOptions = allowPushOptions
				&& isCapabilityEnabled(CAPABILITY_PUSH_OPTIONS);
		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA, MAX_BUF, out);
			msgOut = new SideBandOutputStream(CH_PROGRESS, MAX_BUF, out);
			errOut = new SideBandOutputStream(CH_ERROR, MAX_BUF, out);

			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}
	}
