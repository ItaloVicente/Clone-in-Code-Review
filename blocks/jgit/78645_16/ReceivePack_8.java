	private void receivePackAndCheckConnectivity() throws IOException {
		receivePack();
		if (needCheckConnectivity())
			checkConnectivity();
		parser = null;
	}

	private void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}

	public void sendAdvertisedRefs(final RefAdvertiser adv)
			throws IOException
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
		adv.advertiseCapability(OPTION_AGENT
		adv.send(getAdvertisedOrDefaultRefs());
		for (ObjectId obj : advertisedHaves)
			adv.advertiseHave(obj);
		if (adv.isEmpty())
			adv.advertiseId(ObjectId.zeroId()
		adv.end();
	}

	private void recvCommands() throws IOException {
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

					parseShallow(line.substring(8
					continue;
				}

				if (firstPkt) {
					firstPkt = false;
					FirstLine firstLine = new FirstLine(line);
					enabledCapabilities = firstLine.getCapabilities();
					line = firstLine.getLine();
					enableCapabilities();

					if (line.equals(GitProtocolConstants.OPTION_PUSH_CERT)) {
						certParser.receiveHeader(pckIn
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
			throw new PackProtocolException(e.getMessage()
		}
		clientShallowCommits.add(id);
	}

	static ReceiveCommand parseCommand(String line) throws PackProtocolException {
          if (line == null || line.length() < 83) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		String oldStr = line.substring(0
		String newStr = line.substring(41
		ObjectId oldId
		try {
			oldId = ObjectId.fromString(oldStr);
			newId = ObjectId.fromString(newStr);
		} catch (InvalidObjectIdException e) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef
		}
		String name = line.substring(82);
		if (!Repository.isValidRefName(name)) {
			throw new PackProtocolException(
					JGitText.get().errorInvalidProtocolWantedOldNewRef);
		}
		return new ReceiveCommand(oldId
	}

	private void enableCapabilities() {
		sideBand = isCapabilityEnabled(CAPABILITY_SIDE_BAND_64K);
		quiet = allowQuiet && isCapabilityEnabled(CAPABILITY_QUIET);
		usePushOptions = allowPushOptions
				&& isCapabilityEnabled(CAPABILITY_PUSH_OPTIONS);

		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA
			msgOut = new SideBandOutputStream(CH_PROGRESS
			errOut = new SideBandOutputStream(CH_ERROR

			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}

		reportStatus = isCapabilityEnabled(CAPABILITY_REPORT_STATUS);
	}

	public void setUsePushOptions(boolean usePushOptions) {
		this.usePushOptions = usePushOptions;
	}

	private boolean isCapabilityEnabled(String name) {
		return enabledCapabilities.contains(name);
	}

	private boolean needPack() {
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getType() != ReceiveCommand.Type.DELETE)
				return true;
		}
		return false;
	}

	private void receivePack() throws IOException {
		if (timeoutIn != null)
			timeoutIn.setTimeout(10 * timeout * 1000);

		ProgressMonitor receiving = NullProgressMonitor.INSTANCE;
		ProgressMonitor resolving = NullProgressMonitor.INSTANCE;
		if (sideBand && !quiet)
			resolving = new SideBandProgressMonitor(msgOut);

		try (ObjectInserter ins = db.newObjectInserter()) {
			if (getRefLogIdent() != null)

			parser = ins.newPackParser(rawIn);
			parser.setAllowThin(true);
			parser.setNeedNewObjectIds(checkReferencedIsReachable);
			parser.setNeedBaseObjectIds(checkReferencedIsReachable);
			parser.setCheckEofAfterPackFooter(!biDirectionalPipe
					&& !isExpectDataAfterPackFooter());
			parser.setExpectDataAfterPackFooter(isExpectDataAfterPackFooter());
			parser.setObjectChecker(objectChecker);
			parser.setLockMessage(lockMsg);
			parser.setMaxObjectSizeLimit(maxObjectSizeLimit);
			packLock = parser.parse(receiving
			packSize = Long.valueOf(parser.getPackSize());
			ins.flush();
		}

		if (timeoutIn != null)
			timeoutIn.setTimeout(timeout * 1000);
	}

	private boolean needCheckConnectivity() {
		return isCheckReceivedObjects()
				|| isCheckReferencedObjectsAreReachable()
				|| !getClientShallowCommits().isEmpty();
	}

	private void checkConnectivity() throws IOException {
		ObjectIdSubclassMap<ObjectId> baseObjects = null;
		ObjectIdSubclassMap<ObjectId> providedObjects = null;
		ProgressMonitor checking = NullProgressMonitor.INSTANCE;
		if (sideBand && !quiet) {
			SideBandProgressMonitor m = new SideBandProgressMonitor(msgOut);
			m.setDelayStart(750
			checking = m;
		}

		if (checkReferencedIsReachable) {
			baseObjects = parser.getBaseObjectIds();
			providedObjects = parser.getNewObjectIds();
		}
		parser = null;

		try (final ObjectWalk ow = new ObjectWalk(db)) {
			if (baseObjects != null) {
				ow.sort(RevSort.TOPO);
				if (!baseObjects.isEmpty())
					ow.sort(RevSort.BOUNDARY
			}

			for (final ReceiveCommand cmd : commands) {
				if (cmd.getResult() != Result.NOT_ATTEMPTED)
					continue;
				if (cmd.getType() == ReceiveCommand.Type.DELETE)
					continue;
				ow.markStart(ow.parseAny(cmd.getNewId()));
			}
			for (final ObjectId have : advertisedHaves) {
				RevObject o = ow.parseAny(have);
				ow.markUninteresting(o);

				if (baseObjects != null && !baseObjects.isEmpty()) {
					o = ow.peel(o);
					if (o instanceof RevCommit)
						o = ((RevCommit) o).getTree();
					if (o instanceof RevTree)
						ow.markUninteresting(o);
				}
			}

			checking.beginTask(JGitText.get().countingObjects
					ProgressMonitor.UNKNOWN);
			RevCommit c;
			while ((c = ow.next()) != null) {
				checking.update(1);
						&& !providedObjects.contains(c))
					throw new MissingObjectException(c
			}

			RevObject o;
			while ((o = ow.nextObject()) != null) {
				checking.update(1);
				if (o.has(RevFlag.UNINTERESTING))
					continue;

				if (providedObjects != null) {
					if (providedObjects.contains(o))
						continue;
					else
						throw new MissingObjectException(o
				}

				if (o instanceof RevBlob && !db.hasObject(o))
					throw new MissingObjectException(o
			}
			checking.endTask();

			if (baseObjects != null) {
				for (ObjectId id : baseObjects) {
					o = ow.parseAny(id);
					if (!o.has(RevFlag.UNINTERESTING))
						throw new MissingObjectException(o
				}
			}
		}
	}

	private void validateCommands() {
		for (final ReceiveCommand cmd : commands) {
			final Ref ref = cmd.getRef();
			if (cmd.getResult() != Result.NOT_ATTEMPTED)
				continue;

			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				if (!isAllowDeletes()) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
				if (!isAllowBranchDeletes()
						&& ref.getName().startsWith(Constants.R_HEADS)) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.CREATE) {
				if (!isAllowCreates()) {
					cmd.setResult(Result.REJECTED_NOCREATE);
					continue;
				}

				if (ref != null && !isAllowNonFastForwards()) {
					cmd.setResult(Result.REJECTED_NONFASTFORWARD);
					continue;
				}

				if (ref != null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().refAlreadyExists);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null) {
				ObjectId id = ref.getObjectId();
				if (id == null) {
					id = ObjectId.zeroId();
				}
				if (!ObjectId.zeroId().equals(cmd.getOldId())
						&& !id.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.UPDATE) {
				if (ref == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
					continue;
				}
				ObjectId id = ref.getObjectId();
				if (id == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().cannotUpdateUnbornBranch);
					continue;
				}

				if (!id.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}

				RevObject oldObj
				try {
					oldObj = walk.parseAny(cmd.getOldId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							.getOldId().name());
					continue;
				}

				try {
					newObj = walk.parseAny(cmd.getNewId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							.getNewId().name());
					continue;
				}

				if (oldObj instanceof RevCommit && newObj instanceof RevCommit) {
					try {
						if (walk.isMergedInto((RevCommit) oldObj
								(RevCommit) newObj))
							cmd.setTypeFastForwardUpdate();
						else
							cmd.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT
								.getMessage());
					} catch (IOException e) {
						cmd.setResult(Result.REJECTED_OTHER_REASON);
					}
				} else {
					cmd.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
				}

				if (cmd.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD
						&& !isAllowNonFastForwards()) {
					cmd.setResult(Result.REJECTED_NONFASTFORWARD);
					continue;
				}
			}

			if (!cmd.getRefName().startsWith(Constants.R_REFS)
					|| !Repository.isValidRefName(cmd.getRefName())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON
			}
		}
	}

	private boolean anyRejects() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED && cmd.getResult() != Result.OK)
				return true;
		}
		return false;
	}

	private void failPendingCommands() {
		ReceiveCommand.abort(commands);
	}

	private List<ReceiveCommand> filterCommands(final Result want) {
		return ReceiveCommand.filter(commands
	}

	private void executeCommands() {
		List<ReceiveCommand> toApply = filterCommands(Result.NOT_ATTEMPTED);
		if (toApply.isEmpty())
			return;

		ProgressMonitor updating = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor pm = new SideBandProgressMonitor(msgOut);
			pm.setDelayStart(250
			updating = pm;
		}

		BatchRefUpdate batch = db.getRefDatabase().newBatchUpdate();
		batch.setAllowNonFastForwards(isAllowNonFastForwards());
		batch.setAtomic(isAtomic());
		batch.setRefLogIdent(getRefLogIdent());
		batch.setRefLogMessage("push"
		batch.addCommand(toApply);
		try {
			batch.setPushCertificate(getPushCertificate());
			batch.execute(walk
		} catch (IOException err) {
			for (ReceiveCommand cmd : toApply) {
				if (cmd.getResult() == Result.NOT_ATTEMPTED)
					cmd.reject(err);
			}
		}
	}

	private void sendStatusReport(final boolean forClient
			final Throwable unpackError
		if (unpackError != null) {
			if (forClient) {
				for (final ReceiveCommand cmd : commands) {
				}
			}
			return;
		}

		if (forClient)
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getResult() == Result.OK) {
				if (forClient)
				continue;
			}

			final StringBuilder r = new StringBuilder();
			if (forClient)
			else

			switch (cmd.getResult()) {
			case NOT_ATTEMPTED:
				break;

			case REJECTED_NOCREATE:
				break;

			case REJECTED_NODELETE:
				break;

			case REJECTED_NONFASTFORWARD:
				break;

			case REJECTED_CURRENT_BRANCH:
				break;

			case REJECTED_MISSING_OBJECT:
				if (cmd.getMessage() == null)
				else if (cmd.getMessage().length() == Constants.OBJECT_ID_STRING_LENGTH) {
					r.append(cmd.getMessage());
				} else
					r.append(cmd.getMessage());
				break;

			case REJECTED_OTHER_REASON:
				if (cmd.getMessage() == null)
				else
					r.append(cmd.getMessage());
				break;

			case LOCK_FAILURE:
				break;

			case OK:
				continue;
			}
			if (!forClient)
			out.sendString(r.toString());
		}
	}

	private void close() throws IOException {
		if (sideBand) {
			((SideBandOutputStream) msgOut).flushBuffer();
			((SideBandOutputStream) rawOut).flushBuffer();

			PacketLineOut plo = new PacketLineOut(origOut);
			plo.setFlushOnEnd(false);
			plo.end();
		}

		if (biDirectionalPipe) {
			if (!sideBand && msgOut != null)
				msgOut.flush();
			rawOut.flush();
		}
	}

	private void release() throws IOException {
		walk.close();
		unlockPack();
		timeoutIn = null;
		rawIn = null;
		rawOut = null;
		msgOut = null;
		pckIn = null;
		pckOut = null;
		refs = null;
		commands = null;
		if (timer != null) {
			try {
				timer.terminate();
			} finally {
				timer = null;
			}
		}
	}

	static abstract class Reporter {
			abstract void sendString(String s) throws IOException;
