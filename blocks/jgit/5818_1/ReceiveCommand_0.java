
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_DELETE_REFS;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_OFS_DELTA;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_REPORT_STATUS;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_DATA;
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

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ObjectInserter;
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
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.TimeoutInputStream;
import org.eclipse.jgit.util.io.TimeoutOutputStream;

public abstract class BaseReceivePack {
	public static class FirstLine {
		private final String line;
		private final Set<String> capabilities;
	
		public FirstLine(String line) {
			final HashSet<String> caps = new HashSet<String>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
				for (String c : line.substring(nul + 1).split(" "))
					caps.add(c);
				this.line = line.substring(0
			} else
				this.line = line;
			this.capabilities = Collections.unmodifiableSet(caps);
		}
	
		public String getLine() {
			return line;
		}
	
		public Set<String> getCapabilities() {
			return capabilities;
		}
	}

	protected final Repository db;

	protected final RevWalk walk;

	protected boolean biDirectionalPipe = true;

	protected boolean checkReceivedObjects;

	protected boolean allowCreates;

	protected boolean allowDeletes;

	protected boolean allowNonFastForwards;

	private boolean allowOfsDelta;

	private PersonIdent refLogIdent;

	private AdvertiseRefsHook advertiseRefsHook;

	private RefFilter refFilter;

	protected PreReceiveHook preReceive;

	protected PostReceiveHook postReceive;

	private int timeout;

	private InterruptTimer timer;

	private TimeoutInputStream timeoutIn;

	private OutputStream origOut;

	protected InputStream rawIn;

	protected OutputStream rawOut;

	protected OutputStream msgOut;

	protected PacketLineIn pckIn;

	protected PacketLineOut pckOut;

	private final MessageOutputWrapper msgOutWrapper = new MessageOutputWrapper();

	private PackParser parser;

	protected Map<String

	protected Set<ObjectId> advertisedHaves;

	protected Set<String> enabledCapabilities;

	private List<ReceiveCommand> commands;

	private StringBuilder advertiseError;

	protected boolean reportStatus;

	protected boolean sideBand;

	private PackLock packLock;

	private boolean checkReferencedIsReachable;

	private long maxObjectSizeLimit;

	protected BaseReceivePack(final Repository into) {
		db = into;
		walk = new RevWalk(db);

		final ReceiveConfig cfg = db.getConfig().get(ReceiveConfig.KEY);
		checkReceivedObjects = cfg.checkReceivedObjects;
		allowCreates = cfg.allowCreates;
		allowDeletes = cfg.allowDeletes;
		allowNonFastForwards = cfg.allowNonFastForwards;
		allowOfsDelta = cfg.allowOfsDelta;
		advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
		refFilter = RefFilter.DEFAULT;
		preReceive = PreReceiveHook.NULL;
		postReceive = PostReceiveHook.NULL;
		advertisedHaves = new HashSet<ObjectId>();
	}

	protected static class ReceiveConfig {
		static final SectionParser<ReceiveConfig> KEY = new SectionParser<ReceiveConfig>() {
			public ReceiveConfig parse(final Config cfg) {
				return new ReceiveConfig(cfg);
			}
		};

		final boolean checkReceivedObjects;

		final boolean allowCreates;

		final boolean allowDeletes;

		final boolean allowNonFastForwards;

		final boolean allowOfsDelta;

		ReceiveConfig(final Config config) {
			checkReceivedObjects = config.getBoolean("receive"
					false);
			allowCreates = true;
			allowDeletes = !config.getBoolean("receive"
			allowNonFastForwards = !config.getBoolean("receive"
					"denynonfastforwards"
			allowOfsDelta = config.getBoolean("repack"
					true);
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

	public final Repository getRepository() {
		return db;
	}

	public final RevWalk getRevWalk() {
		return walk;
	}

	public final Map<String
		return refs;
	}

	public void setAdvertisedRefs(Map<String
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);
	
		Ref head = refs.get(Constants.HEAD);
		if (head != null && head.isSymbolic())
			refs.remove(Constants.HEAD);
	
		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null)
				advertisedHaves.add(ref.getObjectId());
		}
		if (additionalHaves != null)
			advertisedHaves.addAll(additionalHaves);
		else
			advertisedHaves.addAll(db.getAdditionalHaves());
	}

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

	public void setBiDirectionalPipe(final boolean twoWay) {
		biDirectionalPipe = twoWay;
	}

	public boolean isCheckReceivedObjects() {
		return checkReceivedObjects;
	}

	public void setCheckReceivedObjects(final boolean check) {
		checkReceivedObjects = check;
	}

	public boolean isAllowCreates() {
		return allowCreates;
	}

	public void setAllowCreates(final boolean canCreate) {
		allowCreates = canCreate;
	}

	public boolean isAllowDeletes() {
		return allowDeletes;
	}

	public void setAllowDeletes(final boolean canDelete) {
		allowDeletes = canDelete;
	}

	public boolean isAllowNonFastForwards() {
		return allowNonFastForwards;
	}

	public void setAllowNonFastForwards(final boolean canRewind) {
		allowNonFastForwards = canRewind;
	}

	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	public void setRefLogIdent(final PersonIdent pi) {
		refLogIdent = pi;
	}

	public AdvertiseRefsHook getAdvertiseRefsHook() {
		return advertiseRefsHook;
	}

	public RefFilter getRefFilter() {
		return refFilter;
	}

	public void setAdvertiseRefsHook(final AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

	public void setRefFilter(final RefFilter refFilter) {
		this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(final int seconds) {
		timeout = seconds;
	}

	public void setMaxObjectSizeLimit(final long limit) {
		maxObjectSizeLimit = limit;
	}

	public boolean isSideBand() throws RequestNotYetReadException {
		if (enabledCapabilities == null)
			throw new RequestNotYetReadException();
		return enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
	}

	public List<ReceiveCommand> getAllCommands() {
		return Collections.unmodifiableList(commands);
	}

	public void sendError(final String what) {
		if (refs == null) {
			if (advertiseError == null)
				advertiseError = new StringBuilder();
			advertiseError.append(what).append('\n');
		} else {
			msgOutWrapper.write(Constants.encode("error: " + what + "\n"));
		}
	}

	public void sendMessage(final String what) {
		msgOutWrapper.write(Constants.encode(what + "\n"));
	}

	public OutputStream getMessageOutputStream() {
		return msgOutWrapper;
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
			timer = new InterruptTimer(caller.getName() + "-Timer");
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

		enabledCapabilities = new HashSet<String>();
		commands = new ArrayList<ReceiveCommand>();
	}

	protected Map<String
		if (refs == null)
			setAdvertisedRefs(null
		return refs;
	}

	protected void receivePackAndCheckConnectivity() throws IOException {
		receivePack();
		if (needCheckConnectivity())
			checkConnectivity();
		parser = null;
	}

	protected void unlockPack() throws IOException {
		if (packLock != null) {
			packLock.unlock();
			packLock = null;
		}
	}

	public void sendAdvertisedRefs(final RefAdvertiser adv)
			throws IOException
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
			adv.advertiseId(ObjectId.zeroId()
		adv.end();
	}

	protected void recvCommands() throws IOException {
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
	
			final ObjectId oldId = ObjectId.fromString(line.substring(0
			final ObjectId newId = ObjectId.fromString(line.substring(41
			final String name = line.substring(82);
			final ReceiveCommand cmd = new ReceiveCommand(oldId
			if (name.equals(Constants.HEAD)) {
				cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
			} else {
				cmd.setRef(refs.get(cmd.getRefName()));
			}
			commands.add(cmd);
		}
	}

	protected void enableCapabilities() {
		reportStatus = enabledCapabilities.contains(CAPABILITY_REPORT_STATUS);
	
		sideBand = enabledCapabilities.contains(CAPABILITY_SIDE_BAND_64K);
		if (sideBand) {
			OutputStream out = rawOut;
	
			rawOut = new SideBandOutputStream(CH_DATA
			msgOut = new SideBandOutputStream(CH_PROGRESS
	
			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);
		}
	}

	protected boolean needPack() {
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
			packLock = parser.parse(receiving
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
				throw new MissingObjectException(c
		}
	
		RevObject o;
		while ((o = ow.nextObject()) != null) {
			if (o.has(RevFlag.UNINTERESTING))
				continue;
	
			if (checkReferencedIsReachable) {
				if (providedObjects.contains(o))
					continue;
				else
					throw new MissingObjectException(o
			}
	
			if (o instanceof RevBlob && !db.hasObject(o))
				throw new MissingObjectException(o
		}
	
		if (checkReferencedIsReachable) {
			for (ObjectId id : baseObjects) {
				o = ow.parseAny(id);
				if (!o.has(RevFlag.UNINTERESTING))
					throw new MissingObjectException(o
			}
		}
	}

	protected void validateCommands() {
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
					cmd.setResult(Result.REJECTED_OTHER_REASON
							.format(JGitText.get().refAlreadyExists
					continue;
				}
			}
	
			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null
					&& !ObjectId.zeroId().equals(cmd.getOldId())
					&& !ref.getObjectId().equals(cmd.getOldId())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON
						JGitText.get().invalidOldIdSent);
				continue;
			}
	
			if (cmd.getType() == ReceiveCommand.Type.UPDATE) {
				if (ref == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
					continue;
				}
	
				if (!ref.getObjectId().equals(cmd.getOldId())) {
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
						if (!walk.isMergedInto((RevCommit) oldObj
								(RevCommit) newObj)) {
							cmd
									.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
						}
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT
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
				cmd.setResult(Result.REJECTED_OTHER_REASON
			}
		}
	}
	
	protected List<ReceiveCommand> filterCommands(final Result want) {
		return ReceiveCommand.filter(commands
	}

	protected void executeCommands() {
		List<ReceiveCommand> toApply = ReceiveCommand.filter(commands
				Result.NOT_ATTEMPTED);
		ProgressMonitor updating = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor pm = new SideBandProgressMonitor(msgOut);
			pm.setDelayStart(250
			updating = pm;
		}
		updating.beginTask(JGitText.get().updatingReferences
		for (ReceiveCommand cmd : toApply) {
			updating.update(1);
			cmd.execute(this);
		}
		updating.endTask();
	}

	protected void sendStatusReport(final boolean forClient
			final Throwable unpackError
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
		walk.release();
		unlockPack();
		timeoutIn = null;
		rawIn = null;
		rawOut = null;
		msgOut = null;
		pckIn = null;
		pckOut = null;
		refs = null;
		enabledCapabilities = null;
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
