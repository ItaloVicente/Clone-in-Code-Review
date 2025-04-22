
package org.eclipse.jgit.lib;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.events.IndexChangedListener;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.events.RepositoryEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.CheckoutEntry;
import org.eclipse.jgit.storage.file.ReflogEntry;
import org.eclipse.jgit.storage.file.ReflogReader;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.io.SafeBufferedOutputStream;

public abstract class AbstractRepository extends Repository {
	private final AtomicInteger useCnt = new AtomicInteger(1);

	private final File gitDir;

	private final FS fs;

	private final ListenerList myListeners = new ListenerList();

	private final File workTree;

	private final File indexFile;

	protected AbstractRepository(final BaseRepositoryBuilder options) {
		super(options);
		gitDir = options.getGitDir();
		fs = options.getFS();
		workTree = options.getWorkTree();
		indexFile = options.getIndexFile();
	}

	@Override
	public ListenerList getListenerList() {
		return myListeners;
	}

	@Override
	public void fireEvent(RepositoryEvent<?> event) {
		event.setRepository(this);
		myListeners.dispatch(event);
		Repository.globalListeners.dispatch(event);
	}

	@Override
	public void create() throws IOException {
		create(false);
	}

	@Override
	public abstract void create(boolean bare) throws IOException;

	@Override
	public File getDirectory() {
		return gitDir;
	}

	@Override
	public abstract ObjectDatabase getObjectDatabase();

	@Override
	public ObjectInserter newObjectInserter() {
		return getObjectDatabase().newInserter();
	}

	@Override
	public ObjectReader newObjectReader() {
		return getObjectDatabase().newReader();
	}

	@Override
	public abstract RefDatabase getRefDatabase();

	@Override
	public abstract StoredConfig getConfig();

	@Override
	public FS getFS() {
		return fs;
	}

	@Override
	public boolean hasObject(AnyObjectId objectId) {
		try {
			return getObjectDatabase().has(objectId);
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public ObjectLoader open(final AnyObjectId objectId)
			throws MissingObjectException
		return getObjectDatabase().open(objectId);
	}

	@Override
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		return getObjectDatabase().open(objectId
	}

	@Override
	public RefUpdate updateRef(final String ref) throws IOException {
		return updateRef(ref
	}

	@Override
	public RefUpdate updateRef(final String ref
		return getRefDatabase().newUpdate(ref
	}

	@Override
	public RefRename renameRef(final String fromRef
		return getRefDatabase().newRename(fromRef
	}

	@Override
	public ObjectId resolve(final String revstr)
			throws AmbiguousObjectException
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw
			if (resolved instanceof String) {
				return getRef((String) resolved).getLeaf().getObjectId();
			} else {
				return (ObjectId) resolved;
			}
		} finally {
			rw.release();
		}
	}

	public String simplify(final String revstr)
			throws AmbiguousObjectException
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw
			if (resolved != null)
				if (resolved instanceof String)
					return (String) resolved;
				else
					return ((AnyObjectId) resolved).getName();
			return null;
		} finally {
			rw.release();
		}
	}

	private Object resolve(final RevWalk rw
			throws IOException {
		char[] revChars = revstr.toCharArray();
		RevObject rev = null;
		String name = null;
		int done = 0;
		for (int i = 0; i < revChars.length; ++i) {
			switch (revChars[i]) {
			case '^':
				if (rev == null) {
					if (name == null)
						name = new String(revChars
					rev = parseSimple(rw
					name = null;
					if (rev == null)
						return null;
				}
				if (i + 1 < revChars.length) {
					switch (revChars[i + 1]) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						int j;
						rev = rw.parseCommit(rev);
						for (j = i + 1; j < revChars.length; ++j) {
							if (!Character.isDigit(revChars[j]))
								break;
						}
						String parentnum = new String(revChars
								- 1);
						int pnum;
						try {
							pnum = Integer.parseInt(parentnum);
						} catch (NumberFormatException e) {
							throw new RevisionSyntaxException(
									JGitText.get().invalidCommitParentNumber
									revstr);
						}
						if (pnum != 0) {
							RevCommit commit = (RevCommit) rev;
							if (pnum > commit.getParentCount())
								rev = null;
							else
								rev = commit.getParent(pnum - 1);
						}
						i = j - 1;
						done = i;
						break;
					case '{':
						int k;
						String item = null;
						for (k = i + 2; k < revChars.length; ++k) {
							if (revChars[k] == '}') {
								item = new String(revChars
								break;
							}
						}
						i = k;
						if (item != null)
							if (item.equals("tree")) {
								rev = rw.parseTree(rev);
							} else if (item.equals("commit")) {
								rev = rw.parseCommit(rev);
							} else if (item.equals("blob")) {
								rev = rw.peel(rev);
								if (!(rev instanceof RevBlob))
									throw new IncorrectObjectTypeException(rev
											Constants.TYPE_BLOB);
							} else if (item.equals("")) {
								rev = rw.peel(rev);
							} else
								throw new RevisionSyntaxException(revstr);
						else
							throw new RevisionSyntaxException(revstr);
						done = k;
						break;
					default:
						rev = rw.parseAny(rev);
						if (rev instanceof RevCommit) {
							RevCommit commit = ((RevCommit) rev);
							if (commit.getParentCount() == 0)
								rev = null;
							else
								rev = commit.getParent(0);
						} else
							throw new IncorrectObjectTypeException(rev
									Constants.TYPE_COMMIT);

					}
				} else {
					rev = rw.peel(rev);
					if (rev instanceof RevCommit) {
						RevCommit commit = ((RevCommit) rev);
						if (commit.getParentCount() == 0)
							rev = null;
						else
							rev = commit.getParent(0);
					} else
						throw new IncorrectObjectTypeException(rev
								Constants.TYPE_COMMIT);
				}
				break;
			case '~':
				if (rev == null) {
					if (name == null)
						name = new String(revChars
					rev = parseSimple(rw
					if (rev == null)
						return null;
				}
				rev = rw.peel(rev);
				if (!(rev instanceof RevCommit))
					throw new IncorrectObjectTypeException(rev
							Constants.TYPE_COMMIT);
				int l;
				for (l = i + 1; l < revChars.length; ++l) {
					if (!Character.isDigit(revChars[l]))
						break;
				}
				int dist;
				if (l - i > 1) {
					String distnum = new String(revChars
					try {
						dist = Integer.parseInt(distnum);
					} catch (NumberFormatException e) {
						throw new RevisionSyntaxException(
								JGitText.get().invalidAncestryLength
					}
				} else
					dist = 1;
				while (dist > 0) {
					RevCommit commit = (RevCommit) rev;
					if (commit.getParentCount() == 0) {
						rev = null;
						break;
					}
					commit = commit.getParent(0);
					rw.parseHeaders(commit);
					rev = commit;
					--dist;
				}
				i = l - 1;
				break;
			case '@':
				if (rev != null)
					throw new RevisionSyntaxException(revstr);
				int m;
				String time = null;
				for (m = i + 2; m < revChars.length; ++m) {
					if (revChars[m] == '}') {
						time = new String(revChars
						break;
					}
				}
				if (time != null) {
					if (time.equals("upstream")) {
						if (name == null)
							name = new String(revChars
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						name = ref.getName();

						RemoteConfig remoteConfig;
						try {
							remoteConfig = new RemoteConfig(getConfig()
									"origin");
						} catch (URISyntaxException e) {
							throw new RevisionSyntaxException(revstr);
						}
						String remoteBranchName = getConfig().getString(
								ConfigConstants.CONFIG_BRANCH_SECTION
								Repository.shortenRefName(ref.getName())
								ConfigConstants.CONFIG_KEY_MERGE);
						List<RefSpec> fetchRefSpecs = remoteConfig
								.getFetchRefSpecs();
						for (RefSpec refSpec : fetchRefSpecs) {
							if (refSpec.matchSource(remoteBranchName)) {
								RefSpec expandFromSource = refSpec
										.expandFromSource(remoteBranchName);
								name = expandFromSource.getDestination();
								break;
							}
						}
						if (name == null)
							throw new RevisionSyntaxException(revstr);
					} else if (time.matches("^-\\d+$")) {
						if (name != null)
							throw new RevisionSyntaxException(revstr);
						else {
							String previousCheckout = resolveReflogCheckout(-Integer
									.parseInt(time));
							if (ObjectId.isId(previousCheckout))
								rev = parseSimple(rw
							else
								name = previousCheckout;
						}
					} else {
						if (name == null)
							name = new String(revChars
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						rev = resolveReflog(rw
						name = null;
					}
					i = m;
				} else
					throw new RevisionSyntaxException(revstr);
				break;
			case ':': {
				RevTree tree;
				if (rev == null) {
					if (name == null)
						name = new String(revChars
					if (name.equals(""))
						name = Constants.HEAD;
					rev = parseSimple(rw
				}
				if (rev == null)
					return null;
				tree = rw.parseTree(rev);
				if (i == revChars.length - 1)
					return tree.copy();

				TreeWalk tw = TreeWalk.forPath(rw.getObjectReader()
						new String(revChars
						tree);
				return tw != null ? tw.getObjectId(0) : null;
			}
			default:
				if (rev != null)
					throw new RevisionSyntaxException(revstr);
			}
		}
		if (rev != null)
			return rev.copy();
		if (name != null)
			return name;
		name = revstr.substring(done);
		if (getRef(name) != null)
			return name;
		return resolveSimple(name);
	}

	private static boolean isHex(char c) {
				|| ('A' <= c && c <= 'F');
	}

	private static boolean isAllHex(String str
		while (ptr < str.length()) {
			if (!isHex(str.charAt(ptr++)))
				return false;
		}
		return true;
	}

	private RevObject parseSimple(RevWalk rw
		ObjectId id = resolveSimple(revstr);
		return id != null ? rw.parseAny(id) : null;
	}

	private ObjectId resolveSimple(final String revstr) throws IOException {
		if (ObjectId.isId(revstr))
			return ObjectId.fromString(revstr);

		Ref r = getRefDatabase().getRef(revstr);
		if (r != null)
			return r.getObjectId();

		if (AbbreviatedObjectId.isId(revstr))
			return resolveAbbreviation(revstr);

		int dashg = revstr.indexOf("-g");
		if ((dashg + 5) < revstr.length() && 0 <= dashg
				&& isHex(revstr.charAt(dashg + 2))
				&& isHex(revstr.charAt(dashg + 3))
				&& isAllHex(revstr
			String s = revstr.substring(dashg + 2);
			if (AbbreviatedObjectId.isId(s))
				return resolveAbbreviation(s);
		}

		return null;
	}

	private String resolveReflogCheckout(int checkoutNo) throws IOException {
		List<ReflogEntry> reflogEntries = new ReflogReader(this
				.getReverseEntries();
		for (ReflogEntry entry : reflogEntries) {
			CheckoutEntry checkout = entry.parseCheckout();
			if (checkout != null)
				if (checkoutNo-- == 1)
					return checkout.getFromBranch();
		}
		return null;
	}

	private RevCommit resolveReflog(RevWalk rw
			throws IOException {
		int number;
		try {
			number = Integer.parseInt(time);
		} catch (NumberFormatException nfe) {
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().invalidReflogRevision
		}
		if (number < 0)
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().invalidReflogRevision

		ReflogReader reader = new ReflogReader(this
		ReflogEntry entry = reader.getReverseEntry(number);
		if (entry == null)
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().reflogEntryNotFound
					Integer.valueOf(number)

		return rw.parseCommit(entry.getNewId());
	}

	private ObjectId resolveAbbreviation(final String revstr) throws IOException
			AmbiguousObjectException {
		AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
		ObjectReader reader = newObjectReader();
		try {
			Collection<ObjectId> matches = reader.resolve(id);
			if (matches.size() == 0)
				return null;
			else if (matches.size() == 1)
				return matches.iterator().next();
			else
				throw new AmbiguousObjectException(id
		} finally {
			reader.release();
		}
	}

	@Override
	public void incrementOpen() {
		useCnt.incrementAndGet();
	}

	@Override
	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			doClose();
		}
	}

	protected void doClose() {
		getObjectDatabase().close();
		getRefDatabase().close();
	}

	@Override
	public String toString() {
		String desc;
		if (getDirectory() != null)
			desc = getDirectory().getPath();
		else
			desc = getClass().getSimpleName() + "-"
					+ System.identityHashCode(this);
		return "Repository[" + desc + "]";
	}

	@Override
	public String getFullBranch() throws IOException {
		Ref head = getRef(Constants.HEAD);
		if (head == null)
			return null;
		if (head.isSymbolic())
			return head.getTarget().getName();
		if (head.getObjectId() != null)
			return head.getObjectId().name();
		return null;
	}

	@Override
	public String getBranch() throws IOException {
		String name = getFullBranch();
		if (name != null)
			return Repository.shortenRefName(name);
		return name;
	}

	@Override
	public Set<ObjectId> getAdditionalHaves() {
		return Collections.emptySet();
	}

	@Override
	public Ref getRef(final String name) throws IOException {
		return getRefDatabase().getRef(name);
	}

	@Override
	public Map<String
		try {
			return getRefDatabase().getRefs(RefDatabase.ALL);
		} catch (IOException e) {
			return new HashMap<String
		}
	}

	@Override
	public Map<String
		try {
			return getRefDatabase().getRefs(Constants.R_TAGS);
		} catch (IOException e) {
			return new HashMap<String
		}
	}

	@Override
	public Ref peel(final Ref ref) {
		try {
			return getRefDatabase().peel(ref);
		} catch (IOException e) {
			return ref;
		}
	}

	@Override
	public Map<AnyObjectId
		Map<String
		Map<AnyObjectId
		for (Ref ref : allRefs.values()) {
			ref = peel(ref);
			AnyObjectId target = ref.getPeeledObjectId();
			if (target == null)
				target = ref.getObjectId();
			Set<Ref> oset = ret.put(target
			if (oset != null) {
				if (oset.size() == 1) {
					oset = new HashSet<Ref>(oset);
				}
				ret.put(target
				oset.add(ref);
			}
		}
		return ret;
	}

	@Override
	public File getIndexFile() throws NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		return indexFile;
	}

	@Override
	public DirCache readDirCache() throws NoWorkTreeException
			CorruptObjectException
		return DirCache.read(this);
	}

	@Override
	public DirCache lockDirCache() throws NoWorkTreeException
			CorruptObjectException
		IndexChangedListener l = new IndexChangedListener() {

			public void onIndexChanged(IndexChangedEvent event) {
				notifyIndexChanged();
			}
		};
		return DirCache.lock(this
	}

	static byte[] gitInternalSlash(byte[] bytes) {
		if (File.separatorChar == '/')
			return bytes;
		for (int i=0; i<bytes.length; ++i)
			if (bytes[i] == File.separatorChar)
				bytes[i] = '/';
		return bytes;
	}

	@Override
	public RepositoryState getRepositoryState() {
		if (isBare() || getDirectory() == null)
			return RepositoryState.BARE;

		if (new File(getWorkTree()
			return RepositoryState.REBASING;
		if (new File(getDirectory()
			return RepositoryState.REBASING_INTERACTIVE;

		if (new File(getDirectory()
			return RepositoryState.REBASING_REBASING;
		if (new File(getDirectory()
			return RepositoryState.APPLY;
		if (new File(getDirectory()
			return RepositoryState.REBASING;

		if (new File(getDirectory()
			return RepositoryState.REBASING_INTERACTIVE;
		if (new File(getDirectory()
			return RepositoryState.REBASING_MERGE;

		if (new File(getDirectory()
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.MERGING_RESOLVED;
				}
			} catch (IOException e) {
			}
			return RepositoryState.MERGING;
		}

		if (new File(getDirectory()
			return RepositoryState.BISECTING;

		if (new File(getDirectory()
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.CHERRY_PICKING_RESOLVED;
				}
			} catch (IOException e) {
			}

			return RepositoryState.CHERRY_PICKING;
		}

		return RepositoryState.SAFE;
	}

	@Override
	public boolean isBare() {
		return workTree == null;
	}

	@Override
	public File getWorkTree() throws NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		return workTree;
	}

	@Override
	public abstract void scanForRepoChanges() throws IOException;

	@Override
	public abstract void notifyIndexChanged();

	@Override
	public abstract ReflogReader getReflogReader(String refName)
			throws IOException;

	@Override
	public String readMergeCommitMsg() throws IOException
		return readCommitMsgFile(Constants.MERGE_MSG);
	}

	@Override
	public void writeMergeCommitMsg(String msg) throws IOException {
		File mergeMsgFile = new File(gitDir
		writeCommitMsg(mergeMsgFile
	}

	@Override
	public List<ObjectId> readMergeHeads() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.MERGE_HEAD);
		if (raw == null)
			return null;

		LinkedList<ObjectId> heads = new LinkedList<ObjectId>();
		for (int p = 0; p < raw.length;) {
			heads.add(ObjectId.fromString(raw
			p = RawParseUtils
					.nextLF(raw
		}
		return heads;
	}

	@Override
	public void writeMergeHeads(List<ObjectId> heads) throws IOException {
		writeHeadsFile(heads
	}

	@Override
	public ObjectId readCherryPickHead() throws IOException
			NoWorkTreeException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.CHERRY_PICK_HEAD);
		if (raw == null)
			return null;

		return ObjectId.fromString(raw
	}

	@Override
	public void writeCherryPickHead(ObjectId head) throws IOException {
		List<ObjectId> heads = (head != null) ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	@Override
	public void writeOrigHead(ObjectId head) throws IOException {
		List<ObjectId> heads = head != null ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	@Override
	public ObjectId readOrigHead() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.ORIG_HEAD);
		return raw != null ? ObjectId.fromString(raw
	}

	@Override
	public String readSquashCommitMsg() throws IOException {
		return readCommitMsgFile(Constants.SQUASH_MSG);
	}

	@Override
	public void writeSquashCommitMsg(String msg) throws IOException {
		File squashMsgFile = new File(gitDir
		writeCommitMsg(squashMsgFile
	}

	private String readCommitMsgFile(String msgFilename) throws IOException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeMsgFile = new File(getDirectory()
		try {
			return RawParseUtils.decode(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	private void writeCommitMsg(File msgFile
		if (msg != null) {
			FileOutputStream fos = new FileOutputStream(msgFile);
			try {
				fos.write(msg.getBytes(Constants.CHARACTER_ENCODING));
			} finally {
				fos.close();
			}
		} else {
			FileUtils.delete(msgFile
		}
	}

	private byte[] readGitDirectoryFile(String filename) throws IOException {
		File file = new File(getDirectory()
		try {
			byte[] raw = IO.readFully(file);
			return raw.length > 0 ? raw : null;
		} catch (FileNotFoundException notFound) {
			return null;
		}
	}

	private void writeHeadsFile(List<ObjectId> heads
			throws FileNotFoundException
		File headsFile = new File(getDirectory()
		if (heads != null) {
			BufferedOutputStream bos = new SafeBufferedOutputStream(
					new FileOutputStream(headsFile));
			try {
				for (ObjectId id : heads) {
					id.copyTo(bos);
					bos.write('\n');
				}
			} finally {
				bos.close();
			}
		} else {
			FileUtils.delete(headsFile
		}
	}
}
