	private void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}

	/**
	 * Generate an advertisement of available refs and capabilities.
	 *
	 * @param adv
	 *            the advertisement formatter.
	 * @throws IOException
	 *             the formatter failed to write an advertisement.
	 * @throws ServiceMayNotContinueException
	 *             the hook denied advertisement.
	 */
	public void sendAdvertisedRefs(final RefAdvertiser adv) throws IOException,
			 ServiceMayNotContinueException {
		if (advertiseError != null) {
			adv.writeOne("ERR " + advertiseError);
			return;
		}

		try {
			advertiseRefsHook.advertiseRefs(this);
		} catch (ServiceMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				adv.writeOne("ERR " + fail.getMessage());
				fail.setOutput();
			}
			throw fail;
		}

		adv.init(db);
		adv.advertiseCapability(CAPABILITY_SIDE_BAND_64K);
		adv.advertiseCapability(CAPABILITY_DELETE_REFS);
		adv.advertiseCapability(CAPABILITY_REPORT_STATUS);
		if (allowOfsDelta)
			adv.advertiseCapability(CAPABILITY_OFS_DELTA);
		adv.send(getAdvertisedOrDefaultRefs());
		for (ObjectId obj : advertisedHaves)
			adv.advertiseHave(obj);
		if (adv.isEmpty())
			adv.advertiseId(ObjectId.zeroId(), "capabilities^{}");
		adv.end();
	}

	private void recvCommands() throws IOException {
		for (;;) {
			String line;
			try {
				line = pckIn.readStringRaw();
			} catch (EOFException eof) {
				if (commands.isEmpty())
					return;
				throw eof;
			}
			if (line == PacketLineIn.END)
				break;

			if (commands.isEmpty()) {
				final FirstLine firstLine = new FirstLine(line);
				enabledCapabilities = firstLine.getCapabilities();
				line = firstLine.getLine();
			}

			if (line.length() < 83) {
				final String m = JGitText.get().errorInvalidProtocolWantedOldNewRef;
				sendError(m);
				throw new PackProtocolException(m);
			}

			final ObjectId oldId = ObjectId.fromString(line.substring(0, 40));
			final ObjectId newId = ObjectId.fromString(line.substring(41, 81));
			final String name = line.substring(82);
			final ReceiveCommand cmd = new ReceiveCommand(oldId, newId, name);
			if (name.equals(Constants.HEAD)) {
				cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
			} else {
				cmd.setRef(refs.get(cmd.getRefName()));
			}
			commands.add(cmd);
		}
	}

	private void enableCapabilities() {
		reportStatus = enabledCapabilities.contains(CAPABILITY_REPORT_STATUS);

		sideBand = enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA, MAX_BUF, out);
			msgOut = new SideBandOutputStream(CH_PROGRESS, MAX_BUF, out);

			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}
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
		if (sideBand)
			resolving = new SideBandProgressMonitor(msgOut);

		ObjectInserter ins = db.newObjectInserter();
		try {
			String lockMsg = "jgit receive-pack";
			if (getRefLogIdent() != null)
				lockMsg += " from " + getRefLogIdent().toExternalString();

			parser = ins.newPackParser(rawIn);
			parser.setAllowThin(true);
			parser.setNeedNewObjectIds(checkReferencedIsReachable);
			parser.setNeedBaseObjectIds(checkReferencedIsReachable);
			parser.setCheckEofAfterPackFooter(!biDirectionalPipe);
			parser.setObjectChecking(isCheckReceivedObjects());
			parser.setLockMessage(lockMsg);
			parser.setMaxObjectSizeLimit(maxObjectSizeLimit);
			packLock = parser.parse(receiving, resolving);
			ins.flush();
		} finally {
			ins.release();
		}

		if (timeoutIn != null)
			timeoutIn.setTimeout(timeout * 1000);
	}

	private boolean needCheckConnectivity() {
		return isCheckReceivedObjects()
				|| isCheckReferencedObjectsAreReachable();
	}

	private void checkConnectivity() throws IOException {
		ObjectIdSubclassMap<ObjectId> baseObjects = null;
		ObjectIdSubclassMap<ObjectId> providedObjects = null;

		if (checkReferencedIsReachable) {
			baseObjects = parser.getBaseObjectIds();
			providedObjects = parser.getNewObjectIds();
		}
		parser = null;

		final ObjectWalk ow = new ObjectWalk(db);
		ow.setRetainBody(false);
		if (checkReferencedIsReachable) {
			ow.sort(RevSort.TOPO);
			if (!baseObjects.isEmpty())
				ow.sort(RevSort.BOUNDARY, true);
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

			if (checkReferencedIsReachable && !baseObjects.isEmpty()) {
				o = ow.peel(o);
				if (o instanceof RevCommit)
					o = ((RevCommit) o).getTree();
				if (o instanceof RevTree)
					ow.markUninteresting(o);
			}
		}

		RevCommit c;
		while ((c = ow.next()) != null) {
					&& !providedObjects.contains(c))
				throw new MissingObjectException(c, Constants.TYPE_COMMIT);
		}

		RevObject o;
		while ((o = ow.nextObject()) != null) {
			if (o.has(RevFlag.UNINTERESTING))
				continue;

			if (checkReferencedIsReachable) {
				if (providedObjects.contains(o))
					continue;
				else
					throw new MissingObjectException(o, o.getType());
			}

			if (o instanceof RevBlob && !db.hasObject(o))
				throw new MissingObjectException(o, Constants.TYPE_BLOB);
		}

		if (checkReferencedIsReachable) {
			for (ObjectId id : baseObjects) {
				o = ow.parseAny(id);
				if (!o.has(RevFlag.UNINTERESTING))
					throw new MissingObjectException(o, o.getType());
			}
		}
	}

	private void validateCommands() {
		for (final ReceiveCommand cmd : commands) {
			final Ref ref = cmd.getRef();
			if (cmd.getResult() != Result.NOT_ATTEMPTED)
				continue;

			if (cmd.getType() == ReceiveCommand.Type.DELETE
					&& !isAllowDeletes()) {
				cmd.setResult(Result.REJECTED_NODELETE);
				continue;
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
					cmd.setResult(Result.REJECTED_OTHER_REASON, MessageFormat
							.format(JGitText.get().refAlreadyExists, ref));
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null
					&& !ObjectId.zeroId().equals(cmd.getOldId())
					&& !ref.getObjectId().equals(cmd.getOldId())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON,
						JGitText.get().invalidOldIdSent);
				continue;
			}

			if (cmd.getType() == ReceiveCommand.Type.UPDATE) {
				if (ref == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON, JGitText.get().noSuchRef);
					continue;
				}

				if (!ref.getObjectId().equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON,
							JGitText.get().invalidOldIdSent);
					continue;
				}

				RevObject oldObj, newObj;
				try {
					oldObj = walk.parseAny(cmd.getOldId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT, cmd
							.getOldId().name());
					continue;
				}

				try {
					newObj = walk.parseAny(cmd.getNewId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT, cmd
							.getNewId().name());
					continue;
				}

				if (oldObj instanceof RevCommit && newObj instanceof RevCommit) {
					try {
						if (!walk.isMergedInto((RevCommit) oldObj,
								(RevCommit) newObj)) {
							cmd
									.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
						}
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT, e
								.getMessage());
					} catch (IOException e) {
						cmd.setResult(Result.REJECTED_OTHER_REASON);
					}
				} else {
					cmd.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
				}
			}

			if (!cmd.getRefName().startsWith(Constants.R_REFS)
					|| !Repository.isValidRefName(cmd.getRefName())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON, JGitText.get().funnyRefname);
			}
		}
	}

	private void executeCommands() {
		preReceive.onPreReceive(this,
				ReceiveCommand.filter(commands, Result.NOT_ATTEMPTED));

		List<ReceiveCommand> toApply = ReceiveCommand.filter(commands,
				Result.NOT_ATTEMPTED);
		ProgressMonitor updating = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor pm = new SideBandProgressMonitor(msgOut);
			pm.setDelayStart(250, TimeUnit.MILLISECONDS);
			updating = pm;
		}
		updating.beginTask(JGitText.get().updatingReferences, toApply.size());
		for (ReceiveCommand cmd : toApply) {
			updating.update(1);
			cmd.execute(this);
		}
		updating.endTask();
	}

	private void sendStatusReport(final boolean forClient, final Reporter out)
			throws IOException {
		if (unpackError != null) {
			out.sendString("unpack error " + unpackError.getMessage());
			if (forClient) {
				for (final ReceiveCommand cmd : commands) {
					out.sendString("ng " + cmd.getRefName()
							+ " n/a (unpacker error)");
				}
			}
			return;
		}

		if (forClient)
			out.sendString("unpack ok");
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getResult() == Result.OK) {
				if (forClient)
					out.sendString("ok " + cmd.getRefName());
				continue;
			}

			final StringBuilder r = new StringBuilder();
			r.append("ng ");
			r.append(cmd.getRefName());
			r.append(" ");

			switch (cmd.getResult()) {
			case NOT_ATTEMPTED:
				r.append("server bug; ref not processed");
				break;

			case REJECTED_NOCREATE:
				r.append("creation prohibited");
				break;

			case REJECTED_NODELETE:
				r.append("deletion prohibited");
				break;

			case REJECTED_NONFASTFORWARD:
				r.append("non-fast forward");
				break;

			case REJECTED_CURRENT_BRANCH:
				r.append("branch is currently checked out");
				break;

			case REJECTED_MISSING_OBJECT:
				if (cmd.getMessage() == null)
					r.append("missing object(s)");
				else if (cmd.getMessage().length() == Constants.OBJECT_ID_STRING_LENGTH)
					r.append("object " + cmd.getMessage() + " missing");
				else
					r.append(cmd.getMessage());
				break;

			case REJECTED_OTHER_REASON:
				if (cmd.getMessage() == null)
					r.append("unspecified reason");
				else
					r.append(cmd.getMessage());
				break;

			case LOCK_FAILURE:
				r.append("failed to lock");
				break;

			case OK:
				continue;
			}
			out.sendString(r.toString());
		}
	}

	static abstract class Reporter {
		abstract void sendString(String s) throws IOException;
