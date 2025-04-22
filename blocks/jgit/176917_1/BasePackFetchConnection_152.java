	private void doFetchV2(ProgressMonitor monitor
			OutputStream outputStream) throws IOException
		sideband = true;
		negotiateBegin();

		String agent = UserAgent.get();
		if (agent != null && isCapableOf(GitProtocolConstants.OPTION_AGENT)) {
			pckState.writeString(
					GitProtocolConstants.OPTION_AGENT + '=' + agent);
		}
		Set<String> capabilities = new HashSet<>();
		String advertised = getCapability(GitProtocolConstants.COMMAND_FETCH);
		if (!StringUtils.isEmptyOrNull(advertised)) {
		}
		pckState.writeDelim();
		for (String capability : getCapabilitiesV2(capabilities)) {
			pckState.writeString(capability);
		}
		if (!sendWants(want
			return;
		}
		outNeedsEnd = false;

		FetchStateV2 fetchState = new FetchStateV2();
		boolean sentDone = false;
		for (;;) {
			state.writeTo(out
			sentDone = sendNextHaveBatch(fetchState
			if (sentDone) {
				break;
			}
			if (readAcknowledgments(fetchState
				break;
			}
		}
		clearState();
		String line = pckIn.readString();
			throw new RemoteRepositoryException(uri
		}
		if (!GitProtocolConstants.SECTION_PACKFILE.equals(line)) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().expectedGot
							GitProtocolConstants.SECTION_PACKFILE
		}
		receivePack(monitor
	}

	private boolean sendNextHaveBatch(FetchStateV2 fetchState
			PacketLineOut output
			throws IOException
		long n = 0;
		while (n < fetchState.havesToSend) {
			final RevCommit c = walk.next();
			if (c == null) {
				break;
			}
			n++;
			if (n % 10 == 0 && monitor.isCancelled()) {
				throw new CancelledException();
			}
		}
		fetchState.havesTotal += n;
		if (n == 0
				|| (fetchState.hadAcks
						&& fetchState.havesWithoutAck > MAX_HAVES)
				|| fetchState.havesTotal > maxHaves) {
			output.end();
			return true;
		}
		fetchState.havesWithoutAck += n;
		output.end();
		fetchState.incHavesToSend(statelessRPC);
		return false;
	}

	private boolean readAcknowledgments(FetchStateV2 fetchState
			PacketLineIn input
			throws IOException
		String line = input.readString();
		if (!GitProtocolConstants.SECTION_ACKNOWLEDGMENTS.equals(line)) {
			throw new PackProtocolException(MessageFormat.format(
					JGitText.get().expectedGot
					GitProtocolConstants.SECTION_ACKNOWLEDGMENTS
		}
		MutableObjectId returnedId = new MutableObjectId();
		line = input.readString();
		boolean gotReady = false;
		long n = 0;
		while (!PacketLineIn.isEnd(line) && !PacketLineIn.isDelimiter(line)) {
			AckNackResult ack = PacketLineIn.parseACKv2(line
			if (!gotReady) {
				if (ack == AckNackResult.ACK_COMMON) {
					markCommon(walk.parseAny(returnedId)
					fetchState.havesWithoutAck = 0;
					fetchState.hadAcks = true;
				} else if (ack == AckNackResult.ACK_READY) {
					gotReady = true;
				}
			}
			n++;
			if (n % 10 == 0 && monitor.isCancelled()) {
				throw new CancelledException();
			}
			line = input.readString();
		}
		if (gotReady) {
			if (!PacketLineIn.isDelimiter(line)) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().expectedGot
			}
		} else if (!PacketLineIn.isEnd(line)) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().expectedGot
		}
		return gotReady;
	}

