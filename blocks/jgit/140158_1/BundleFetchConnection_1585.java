
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_ATOMIC;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_DELETE_REFS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_OFS_DELTA;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_PUSH_OPTIONS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_QUIET;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_REPORT_STATUS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_DATA;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_ERROR;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_PROGRESS;
import static org.eclipse.jgit.transport.SideBandOutputStream.MAX_BUF;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TooLargePackException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator.SubmoduleValidationException;
import org.eclipse.jgit.internal.transport.parser.FirstCommand;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitmoduleEntry;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PacketLineIn.InputOverLimitIOException;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.LimitedInputStream;
import org.eclipse.jgit.util.io.TimeoutInputStream;
import org.eclipse.jgit.util.io.TimeoutOutputStream;

public abstract class BaseReceivePack {
	@Deprecated
	public static class FirstLine {
		private final FirstCommand command;

		public FirstLine(String line) {
			command = FirstCommand.fromLine(line);
		}

		public String getLine() {
			return command.getLine();
		}

		public Set<String> getCapabilities() {
			return command.getCapabilities();
		}
	}

	final Repository db;

	final RevWalk walk;

	private boolean biDirectionalPipe = true;

	private boolean expectDataAfterPackFooter;

	private ObjectChecker objectChecker;

	private boolean allowCreates;

	private boolean allowAnyDeletes;
	private boolean allowBranchDeletes;

	private boolean allowNonFastForwards;

	private boolean allowPushOptions;

	private boolean atomic;

	private boolean allowOfsDelta;
	private boolean allowQuiet = true;

	private PersonIdent refLogIdent;

	private AdvertiseRefsHook advertiseRefsHook;

	RefFilter refFilter;

	private int timeout;

	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private OutputStream origOut;

	protected InputStream rawIn;

	protected OutputStream rawOut;

	protected OutputStream msgOut;
	private SideBandOutputStream errOut;

	protected PacketLineIn pckIn;

	protected PacketLineOut pckOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PackParser parser;

	Map<String

	Set<ObjectId> advertisedHaves;

	private Set<String> enabledCapabilities;
	String userAgent;
	private Set<ObjectId> clientShallowCommits;
	private List<ReceiveCommand> commands;
	private long maxCommandBytes;
	private long maxDiscardBytes;

	private StringBuilder advertiseError;

	private boolean sideBand;

	private boolean quiet;

	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	private long maxObjectSizeLimit;

	private long maxPackSizeLimit = -1;

	private Long packSize;

	private PushCertificateParser pushCertificateParser;
	private SignedPushConfig signedPushConfig;
	PushCertificate pushCert;
	private ReceivedPackStatistics stats;

	@Deprecated
	public abstract PushCertificate getPushCertificate();

	@Deprecated
	public abstract void setPushCertificate(PushCertificate cert);

	protected BaseReceivePack(Repository into) {
		db = into;
		walk = new RevWalk(db);
		walk.setRetainBody(false);

		TransferConfig tc = db.getConfig().get(TransferConfig.KEY);
		objectChecker = tc.newReceiveObjectChecker();

		ReceiveConfig rc = db.getConfig().get(ReceiveConfig::new);
		allowCreates = rc.allowCreates;
		allowAnyDeletes = true;
		allowBranchDeletes = rc.allowDeletes;
		allowNonFastForwards = rc.allowNonFastForwards;
		allowOfsDelta = rc.allowOfsDelta;
		allowPushOptions = rc.allowPushOptions;
		maxCommandBytes = rc.maxCommandBytes;
		maxDiscardBytes = rc.maxDiscardBytes;
		advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
		refFilter = RefFilter.DEFAULT;
		advertisedHaves = new HashSet<>();
		clientShallowCommits = new HashSet<>();
		signedPushConfig = rc.signedPush;
	}

	protected static class ReceiveConfig {
		final boolean allowCreates;
		final boolean allowDeletes;
		final boolean allowNonFastForwards;
		final boolean allowOfsDelta;
		final boolean allowPushOptions;
		final long maxCommandBytes;
		final long maxDiscardBytes;
		final SignedPushConfig signedPush;

		ReceiveConfig(Config config) {
			allowCreates = true;
			allowDeletes = !config.getBoolean("receive"
			allowNonFastForwards = !config.getBoolean("receive"
					"denynonfastforwards"
			allowOfsDelta = config.getBoolean("repack"
					true);
			allowPushOptions = config.getBoolean("receive"
					false);
			maxCommandBytes = config.getLong("receive"
					"maxCommandBytes"
					3 << 20);
			maxDiscardBytes = config.getLong("receive"
					"maxCommandDiscardBytes"
					-1);
			signedPush = SignedPushConfig.KEY.parse(config);
		}
	}

	class MessageOutputWrapper extends OutputStream {
		@Override
		public void write(int ch) {
			if (msgOut != null) {
				try {
					msgOut.write(ch);
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b
			if (msgOut != null) {
				try {
					msgOut.write(b
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b) {
			write(b
		}

		@Override
		public void flush() {
			if (msgOut != null) {
				try {
					msgOut.flush();
				} catch (IOException e) {
				}
			}
		}
	}

	protected abstract String getLockMessageProcessName();

	@Deprecated
	public abstract Repository getRepository();

	@Deprecated
	public abstract RevWalk getRevWalk();

	@Deprecated
	public abstract Map<String

	@Deprecated
	public abstract void setAdvertisedRefs(Map<String

	public final Set<ObjectId> getAdvertisedObjects() {
		return advertisedHaves;
	}

	public boolean isCheckReferencedObjectsAreReachable() {
		return checkReferencedIsReachable;
	}

	public void setCheckReferencedObjectsAreReachable(boolean b) {
		this.checkReferencedIsReachable = b;
	}

	public boolean isBiDirectionalPipe() {
		return biDirectionalPipe;
	}

	public void setBiDirectionalPipe(boolean twoWay) {
		biDirectionalPipe = twoWay;
	}

	public boolean isExpectDataAfterPackFooter() {
		return expectDataAfterPackFooter;
	}

	public void setExpectDataAfterPackFooter(boolean e) {
		expectDataAfterPackFooter = e;
	}

	public boolean isCheckReceivedObjects() {
		return objectChecker != null;
	}

	public void setCheckReceivedObjects(boolean check) {
		if (check && objectChecker == null)
			setObjectChecker(new ObjectChecker());
		else if (!check && objectChecker != null)
			setObjectChecker(null);
	}

	public void setObjectChecker(ObjectChecker impl) {
		objectChecker = impl;
	}

	public boolean isAllowCreates() {
		return allowCreates;
	}

	public void setAllowCreates(boolean canCreate) {
		allowCreates = canCreate;
	}

	public boolean isAllowDeletes() {
		return allowAnyDeletes;
	}

	public void setAllowDeletes(boolean canDelete) {
		allowAnyDeletes = canDelete;
	}

	public boolean isAllowBranchDeletes() {
		return allowBranchDeletes;
	}

	public void setAllowBranchDeletes(boolean canDelete) {
		allowBranchDeletes = canDelete;
	}

	public boolean isAllowNonFastForwards() {
		return allowNonFastForwards;
	}

	public void setAllowNonFastForwards(boolean canRewind) {
		allowNonFastForwards = canRewind;
	}

	public boolean isAtomic() {
		return atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	public void setRefLogIdent(PersonIdent pi) {
		refLogIdent = pi;
	}

	public AdvertiseRefsHook getAdvertiseRefsHook() {
		return advertiseRefsHook;
	}

	public RefFilter getRefFilter() {
		return refFilter;
	}

	public void setAdvertiseRefsHook(AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

	public void setRefFilter(RefFilter refFilter) {
		this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int seconds) {
		timeout = seconds;
	}

	public void setMaxCommandBytes(long limit) {
		maxCommandBytes = limit;
	}

	public void setMaxCommandDiscardBytes(long limit) {
		maxDiscardBytes = limit;
	}

	public void setMaxObjectSizeLimit(long limit) {
		maxObjectSizeLimit = limit;
	}

	public void setMaxPackSizeLimit(long limit) {
		if (limit < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().receivePackInvalidLimit
		maxPackSizeLimit = limit;
	}

	public boolean isSideBand() throws RequestNotYetReadException {
		checkRequestWasRead();
		return enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
	}

	public boolean isAllowQuiet() {
		return allowQuiet;
	}

	public void setAllowQuiet(boolean allow) {
		allowQuiet = allow;
	}

	public boolean isAllowPushOptions() {
		return allowPushOptions;
	}

	public void setAllowPushOptions(boolean allow) {
		allowPushOptions = allow;
	}

	public boolean isQuiet() throws RequestNotYetReadException {
		checkRequestWasRead();
		return quiet;
	}

	public void setSignedPushConfig(SignedPushConfig cfg) {
		signedPushConfig = cfg;
	}

	private PushCertificateParser getPushCertificateParser() {
		if (pushCertificateParser == null) {
			pushCertificateParser = new PushCertificateParser(db
		}
		return pushCertificateParser;
	}

	public String getPeerUserAgent() {
		return UserAgent.getAgent(enabledCapabilities
	}

	public List<ReceiveCommand> getAllCommands() {
		return Collections.unmodifiableList(commands);
	}

	public void sendError(String what) {
		if (refs == null) {
			if (advertiseError == null)
				advertiseError = new StringBuilder();
			advertiseError.append(what).append('\n');
		} else {
		}
	}

	private void fatalError(String msg) {
		if (errOut != null) {
			try {
				errOut.write(Constants.encode(msg));
				errOut.flush();
			} catch (IOException e) {
			}
		} else {
			sendError(msg);
		}
	}

	public void sendMessage(String what) {
	}

	public OutputStream getMessageOutputStream() {
		return msgOutWrapper;
	}

	public long getPackSize() {
		if (packSize != null)
			return packSize.longValue();
		throw new IllegalStateException(JGitText.get().packSizeNotSetYet);
	}

	protected Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

	protected boolean hasCommands() {
		return !commands.isEmpty();
	}

	protected boolean hasError() {
		return advertiseError != null;
	}

	protected void init(final InputStream input
			final OutputStream messages) {
		origOut = output;
		rawIn = input;
		rawOut = output;
		msgOut = messages;

		if (timeout > 0) {
			final Thread caller = Thread.currentThread();
			timeoutIn = new TimeoutInputStream(rawIn
			TimeoutOutputStream o = new TimeoutOutputStream(rawOut
			timeoutIn.setTimeout(timeout * 1000);
			o.setTimeout(timeout * 1000);
			rawIn = timeoutIn;
			rawOut = o;
		}

		pckIn = new PacketLineIn(rawIn);
		pckOut = new PacketLineOut(rawOut);
		pckOut.setFlushOnEnd(false);

		enabledCapabilities = new HashSet<>();
		commands = new ArrayList<>();
	}

	protected Map<String
		if (refs == null)
			setAdvertisedRefs(null
		return refs;
	}

	protected void receivePackAndCheckConnectivity() throws IOException {
		receivePack();
		if (needCheckConnectivity()) {
			checkSubmodules();
			checkConnectivity();
		}
		parser = null;
	}

	protected void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}

	public void sendAdvertisedRefs(RefAdvertiser adv)
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
		}
		adv.advertiseCapability(OPTION_AGENT
		adv.send(getAdvertisedOrDefaultRefs().values());
		for (ObjectId obj : advertisedHaves)
			adv.advertiseHave(obj);
		if (adv.isEmpty())
			adv.advertiseId(ObjectId.zeroId()
		adv.end();
	}

	@Nullable
	public ReceivedPackStatistics getReceivedPackStatistics() {
		return stats;
	}

	protected void recvCommands() throws IOException {
		PacketLineIn pck = maxCommandBytes > 0
				? new PacketLineIn(rawIn
				: pckIn;
		PushCertificateParser certParser = getPushCertificateParser();
		boolean firstPkt = true;
		try {
			for (;;) {
				String line;
				try {
					line = pck.readString();
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
					FirstCommand firstLine = FirstCommand.fromLine(line);
					enabledCapabilities = firstLine.getCapabilities();
					line = firstLine.getLine();
					enableCapabilities();

					if (line.equals(GitProtocolConstants.OPTION_PUSH_CERT)) {
						certParser.receiveHeader(pck
						continue;
					}
				}

				if (line.equals(PushCertificateParser.BEGIN_SIGNATURE)) {
					certParser.receiveSignature(pck);
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
			if (hasCommands()) {
				readPostCommands(pck);
			}
		} catch (PackProtocolException e) {
			discardCommands();
			fatalError(e.getMessage());
			throw e;
		} catch (InputOverLimitIOException e) {
			String msg = JGitText.get().tooManyCommands;
			discardCommands();
			fatalError(msg);
			throw new PackProtocolException(msg);
		}
	}

	private void discardCommands() {
		if (sideBand) {
			long max = maxDiscardBytes;
			if (max < 0) {
				max = Math.max(3 * maxCommandBytes
			}
			try {
				new PacketLineIn(rawIn
			} catch (IOException e) {
			}
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

	void readPostCommands(PacketLineIn in) throws IOException {
	}

	protected void enableCapabilities() {
		sideBand = isCapabilityEnabled(CAPABILITY_SIDE_BAND_64K);
		quiet = allowQuiet && isCapabilityEnabled(CAPABILITY_QUIET);
		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA
			msgOut = new SideBandOutputStream(CH_PROGRESS
			errOut = new SideBandOutputStream(CH_ERROR

			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}
	}

	protected boolean isCapabilityEnabled(String name) {
		return enabledCapabilities.contains(name);
	}

	void checkRequestWasRead() {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
	}

	protected boolean needPack() {
		for (ReceiveCommand cmd : commands) {
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

			parser = ins.newPackParser(packInputStream());
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
			stats = parser.getReceivedPackStatistics();
			ins.flush();
		}

		if (timeoutIn != null)
			timeoutIn.setTimeout(timeout * 1000);
	}

	private InputStream packInputStream() {
		InputStream packIn = rawIn;
		if (maxPackSizeLimit >= 0) {
			packIn = new LimitedInputStream(packIn
				@Override
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};
		}
		return packIn;
	}

	private boolean needCheckConnectivity() {
		return isCheckReceivedObjects()
				|| isCheckReferencedObjectsAreReachable()
				|| !getClientShallowCommits().isEmpty();
	}

	private void checkSubmodules()
			throws IOException {
		ObjectDatabase odb = db.getObjectDatabase();
		if (objectChecker == null) {
			return;
		}
		for (GitmoduleEntry entry : objectChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = odb.open(blobId

			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (LargeObjectException | SubmoduleValidationException e) {
				throw new IOException(e);
			}
		}
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

		try (ObjectWalk ow = new ObjectWalk(db)) {
			if (baseObjects != null) {
				ow.sort(RevSort.TOPO);
				if (!baseObjects.isEmpty())
					ow.sort(RevSort.BOUNDARY
			}

			for (ReceiveCommand cmd : commands) {
				if (cmd.getResult() != Result.NOT_ATTEMPTED)
					continue;
				if (cmd.getType() == ReceiveCommand.Type.DELETE)
					continue;
				ow.markStart(ow.parseAny(cmd.getNewId()));
			}
			for (ObjectId have : advertisedHaves) {
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

				if (o instanceof RevBlob && !db.getObjectDatabase().has(o))
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

	protected void validateCommands() {
		for (ReceiveCommand cmd : commands) {
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

	protected boolean anyRejects() {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED && cmd.getResult() != Result.OK)
				return true;
		}
		return false;
	}

	protected void failPendingCommands() {
		ReceiveCommand.abort(commands);
	}

	protected List<ReceiveCommand> filterCommands(Result want) {
		return ReceiveCommand.filter(commands
	}

	protected void executeCommands() {
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

	protected void sendStatusReport(final boolean forClient
			final Throwable unpackError
		if (unpackError != null) {
			if (forClient) {
				for (ReceiveCommand cmd : commands) {
				}
			}
			return;
		}

		if (forClient)
		for (ReceiveCommand cmd : commands) {
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

	static abstract class Reporter {
			abstract void sendString(String s) throws IOException;
	}
}
