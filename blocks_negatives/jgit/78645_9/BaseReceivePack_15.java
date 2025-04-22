	public void setUsePushOptions(boolean usePushOptions) {
		this.usePushOptions = usePushOptions;
	}

	/**
	 * Check if the peer requested a capability.
	 *
	 * @param name
	 *            protocol name identifying the capability.
	 * @return true if the peer requested the capability to be enabled.
	 */
	protected boolean isCapabilityEnabled(String name) {
		return enabledCapabilities.contains(name);
	}

	/** @return true if a pack is expected based on the list of commands. */
	protected boolean needPack() {
		for (final ReceiveCommand cmd : commands) {
			if (cmd.getType() != ReceiveCommand.Type.DELETE)
				return true;
		}
		return false;
	}

	/**
	 * Receive a pack from the input and store it in the repository.
	 *
	 * @throws IOException
	 *             an error occurred reading or indexing the pack.
	 */
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
			packLock = parser.parse(receiving, resolving);
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
			m.setDelayStart(750, TimeUnit.MILLISECONDS);
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

				if (baseObjects != null && !baseObjects.isEmpty()) {
					o = ow.peel(o);
					if (o instanceof RevCommit)
						o = ((RevCommit) o).getTree();
					if (o instanceof RevTree)
						ow.markUninteresting(o);
				}
			}

			checking.beginTask(JGitText.get().countingObjects,
					ProgressMonitor.UNKNOWN);
			RevCommit c;
			while ((c = ow.next()) != null) {
				checking.update(1);
						&& !providedObjects.contains(c))
					throw new MissingObjectException(c, Constants.TYPE_COMMIT);
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
						throw new MissingObjectException(o, o.getType());
				}

				if (o instanceof RevBlob && !db.hasObject(o))
					throw new MissingObjectException(o, Constants.TYPE_BLOB);
			}
			checking.endTask();

			if (baseObjects != null) {
				for (ObjectId id : baseObjects) {
					o = ow.parseAny(id);
					if (!o.has(RevFlag.UNINTERESTING))
						throw new MissingObjectException(o, o.getType());
				}
			}
		}
	}

	/** Validate the command list. */
	protected void validateCommands() {
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
					cmd.setResult(Result.REJECTED_OTHER_REASON,
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
					cmd.setResult(Result.REJECTED_OTHER_REASON,
							JGitText.get().invalidOldIdSent);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.UPDATE) {
				if (ref == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON, JGitText.get().noSuchRef);
					continue;
				}
				ObjectId id = ref.getObjectId();
				if (id == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON,
							JGitText.get().cannotUpdateUnbornBranch);
					continue;
				}

				if (!id.equals(cmd.getOldId())) {
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
						if (walk.isMergedInto((RevCommit) oldObj,
								(RevCommit) newObj))
							cmd.setTypeFastForwardUpdate();
						else
							cmd.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT, e
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
				cmd.setResult(Result.REJECTED_OTHER_REASON, JGitText.get().funnyRefname);
			}
		}
	}

	/**
	 * @return if any commands have been rejected so far.
	 * @since 3.6
	 */
	protected boolean anyRejects() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED && cmd.getResult() != Result.OK)
				return true;
		}
		return false;
	}

	/**
	 * Set the result to fail for any command that was not processed yet.
	 * @since 3.6
	 */
	protected void failPendingCommands() {
		ReceiveCommand.abort(commands);
	}

	/**
	 * Filter the list of commands according to result.
	 *
	 * @param want
	 *            desired status to filter by.
	 * @return a copy of the command list containing only those commands with the
	 *         desired status.
	 */
	protected List<ReceiveCommand> filterCommands(final Result want) {
		return ReceiveCommand.filter(commands, want);
	}

	/** Execute commands to update references. */
	protected void executeCommands() {
		List<ReceiveCommand> toApply = filterCommands(Result.NOT_ATTEMPTED);
		if (toApply.isEmpty())
			return;

		ProgressMonitor updating = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor pm = new SideBandProgressMonitor(msgOut);
			pm.setDelayStart(250, TimeUnit.MILLISECONDS);
			updating = pm;
		}

		BatchRefUpdate batch = db.getRefDatabase().newBatchUpdate();
		batch.setAllowNonFastForwards(isAllowNonFastForwards());
		batch.setAtomic(isAtomic());
		batch.setRefLogIdent(getRefLogIdent());
		batch.setRefLogMessage("push", true); //$NON-NLS-1$
		batch.addCommand(toApply);
		try {
			batch.setPushCertificate(getPushCertificate());
			batch.execute(walk, updating);
		} catch (IOException err) {
			for (ReceiveCommand cmd : toApply) {
				if (cmd.getResult() == Result.NOT_ATTEMPTED)
					cmd.reject(err);
			}
		}
	}

	/**
	 * Send a status report.
	 *
	 * @param forClient
	 *            true if this report is for a Git client, false if it is for an
	 *            end-user.
	 * @param unpackError
	 *            an error that occurred during unpacking, or {@code null}
	 * @param out
	 *            the reporter for sending the status strings.
	 * @throws IOException
	 *             an error occurred writing the status report.
	 */
	protected void sendStatusReport(final boolean forClient,
			final Throwable unpackError, final Reporter out) throws IOException {
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

	/**
	 * Close and flush (if necessary) the underlying streams.
	 *
	 * @throws IOException
	 */
	protected void close() throws IOException {
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

	/**
	 * Release any resources used by this object.
	 *
	 * @throws IOException
	 *             the pack could not be unlocked.
	 */
	protected void release() throws IOException {
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

	/** Interface for reporting status messages. */
	static abstract class Reporter {
			abstract void sendString(String s) throws IOException;
	}
