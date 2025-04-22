
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.LOCK_SUFFIX;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
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
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Repository implements AutoCloseable {
	private static final Logger LOG = LoggerFactory.getLogger(Repository.class);
	private static final ListenerList globalListeners = new ListenerList();

	private static final Pattern FORBIDDEN_BRANCH_NAME_COMPONENTS = Pattern
			.compile(
					"(^|/)(aux|com[1-9]|con|lpt[1-9]|nul|prn)(\\.[^/]*)?"
					Pattern.CASE_INSENSITIVE);

	public static ListenerList getGlobalListenerList() {
		return globalListeners;
	}

	final AtomicInteger useCnt = new AtomicInteger(1);

	final AtomicLong closedAt = new AtomicLong();

	private final File gitDir;

	private final FS fs;

	private final ListenerList myListeners = new ListenerList();

	private final File workTree;

	private final File indexFile;

	protected Repository(BaseRepositoryBuilder options) {
		gitDir = options.getGitDir();
		fs = options.getFS();
		workTree = options.getWorkTree();
		indexFile = options.getIndexFile();
	}

	@NonNull
	public ListenerList getListenerList() {
		return myListeners;
	}

	public void fireEvent(RepositoryEvent<?> event) {
		event.setRepository(this);
		myListeners.dispatch(event);
		globalListeners.dispatch(event);
	}

	public void create() throws IOException {
		create(false);
	}

	public abstract void create(boolean bare) throws IOException;

	public File getDirectory() {
		return gitDir;
	}

	@NonNull
	public abstract ObjectDatabase getObjectDatabase();

	@NonNull
	public ObjectInserter newObjectInserter() {
		return getObjectDatabase().newInserter();
	}

	@NonNull
	public ObjectReader newObjectReader() {
		return getObjectDatabase().newReader();
	}

	@NonNull
	public abstract RefDatabase getRefDatabase();

	@NonNull
	public abstract StoredConfig getConfig();

	@NonNull
	public abstract AttributesNodeProvider createAttributesNodeProvider();

	public FS getFS() {
		return fs;
	}

	@Deprecated
	public boolean hasObject(AnyObjectId objectId) {
		try {
			return getObjectDatabase().has(objectId);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@NonNull
	public ObjectLoader open(AnyObjectId objectId)
			throws MissingObjectException
		return getObjectDatabase().open(objectId);
	}

	@NonNull
	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		return getObjectDatabase().open(objectId
	}

	@NonNull
	public RefUpdate updateRef(String ref) throws IOException {
		return updateRef(ref
	}

	@NonNull
	public RefUpdate updateRef(String ref
		return getRefDatabase().newUpdate(ref
	}

	@NonNull
	public RefRename renameRef(String fromRef
		return getRefDatabase().newRename(fromRef
	}

	@Nullable
	public ObjectId resolve(String revstr)
			throws AmbiguousObjectException
			RevisionSyntaxException
		try (RevWalk rw = new RevWalk(this)) {
			rw.setRetainBody(false);
			Object resolved = resolve(rw
			if (resolved instanceof String) {
				final Ref ref = findRef((String) resolved);
				return ref != null ? ref.getLeaf().getObjectId() : null;
			} else {
				return (ObjectId) resolved;
			}
		}
	}

	@Nullable
	public String simplify(String revstr)
			throws AmbiguousObjectException
		try (RevWalk rw = new RevWalk(this)) {
			rw.setRetainBody(true);
			Object resolved = resolve(rw
			if (resolved != null)
				if (resolved instanceof String)
					return (String) resolved;
				else
					return ((AnyObjectId) resolved).getName();
			return null;
		}
	}

	@Nullable
	private Object resolve(RevWalk rw
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
						if (done == 0)
							name = new String(revChars
						else {
							done = i + 1;
							break;
						}
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
						done = j;
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
								rev = rw.parseTree(rev);
								rev = rw.parseCommit(rev);
								rev = rw.peel(rev);
								if (!(rev instanceof RevBlob))
									throw new IncorrectObjectTypeException(rev
											Constants.TYPE_BLOB);
								rev = rw.peel(rev);
							} else
								throw new RevisionSyntaxException(revstr);
						else
							throw new RevisionSyntaxException(revstr);
						done = k;
						break;
					default:
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
				done = i + 1;
				break;
			case '~':
				if (rev == null) {
					if (name == null)
						if (done == 0)
							name = new String(revChars
						else {
							done = i + 1;
							break;
						}
					rev = parseSimple(rw
					name = null;
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
				done = l;
				break;
			case '@':
				if (rev != null)
					throw new RevisionSyntaxException(revstr);
				if (i + 1 == revChars.length)
					continue;
				if (i + 1 < revChars.length && revChars[i + 1] != '{')
					continue;
				int m;
				String time = null;
				for (m = i + 2; m < revChars.length; ++m) {
					if (revChars[m] == '}') {
						time = new String(revChars
						break;
					}
				}
				if (time != null) {
						if (name == null)
							name = new String(revChars
							name = Constants.HEAD;
							throw new RevisionSyntaxException(MessageFormat
									.format(JGitText.get().invalidRefName
											name)
									revstr);
						Ref ref = findRef(name);
						name = null;
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						name = ref.getName();

						RemoteConfig remoteConfig;
						try {
							remoteConfig = new RemoteConfig(getConfig()
						} catch (URISyntaxException e) {
							throw new RevisionSyntaxException(revstr);
						}
						String remoteBranchName = getConfig()
								.getString(
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
							name = Constants.HEAD;
							throw new RevisionSyntaxException(MessageFormat
									.format(JGitText.get().invalidRefName
											name)
									revstr);
						Ref ref = findRef(name);
						name = null;
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						rev = resolveReflog(rw
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
						name = Constants.HEAD;
					rev = parseSimple(rw
					name = null;
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
		if (done == revstr.length())
			return null;
		name = revstr.substring(done);
			throw new RevisionSyntaxException(
					MessageFormat.format(JGitText.get().invalidRefName
					revstr);
		if (findRef(name) != null)
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

	@Nullable
	private RevObject parseSimple(RevWalk rw
		ObjectId id = resolveSimple(revstr);
		return id != null ? rw.parseAny(id) : null;
	}

	@Nullable
	private ObjectId resolveSimple(String revstr) throws IOException {
		if (ObjectId.isId(revstr))
			return ObjectId.fromString(revstr);

			Ref r = getRefDatabase().findRef(revstr);
			if (r != null)
				return r.getObjectId();
		}

		if (AbbreviatedObjectId.isId(revstr))
			return resolveAbbreviation(revstr);

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

	@Nullable
	private String resolveReflogCheckout(int checkoutNo)
			throws IOException {
		ReflogReader reader = getReflogReader(Constants.HEAD);
		if (reader == null) {
			return null;
		}
		List<ReflogEntry> reflogEntries = reader.getReverseEntries();
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
		assert number >= 0;
		ReflogReader reader = getReflogReader(ref.getName());
		if (reader == null) {
			throw new RevisionSyntaxException(
					MessageFormat.format(JGitText.get().reflogEntryNotFound
							Integer.valueOf(number)
		}
		ReflogEntry entry = reader.getReverseEntry(number);
		if (entry == null)
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().reflogEntryNotFound
					Integer.valueOf(number)

		return rw.parseCommit(entry.getNewId());
	}

	@Nullable
	private ObjectId resolveAbbreviation(String revstr) throws IOException
			AmbiguousObjectException {
		AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
		try (ObjectReader reader = newObjectReader()) {
			Collection<ObjectId> matches = reader.resolve(id);
			if (matches.isEmpty())
				return null;
			else if (matches.size() == 1)
				return matches.iterator().next();
			else
				throw new AmbiguousObjectException(id
		}
	}

	public void incrementOpen() {
		useCnt.incrementAndGet();
	}

	@Override
	public void close() {
		int newCount = useCnt.decrementAndGet();
		if (newCount == 0) {
			if (RepositoryCache.isCached(this)) {
				closedAt.set(System.currentTimeMillis());
			} else {
				doClose();
			}
		} else if (newCount == -1) {
			String message = MessageFormat.format(JGitText.get().corruptUseCnt
					toString());
			if (LOG.isDebugEnabled()) {
				LOG.debug(message
			} else {
				LOG.warn(message);
			}
			if (RepositoryCache.isCached(this)) {
				closedAt.set(System.currentTimeMillis());
			}
		}
	}

	protected void doClose() {
		getObjectDatabase().close();
		getRefDatabase().close();
	}

	@Override
	@NonNull
	public String toString() {
		String desc;
		File directory = getDirectory();
		if (directory != null)
			desc = directory.getPath();
		else
					+ System.identityHashCode(this);
	}

	@Nullable
	public String getFullBranch() throws IOException {
		Ref head = exactRef(Constants.HEAD);
		if (head == null) {
			return null;
		}
		if (head.isSymbolic()) {
			return head.getTarget().getName();
		}
		ObjectId objectId = head.getObjectId();
		if (objectId != null) {
			return objectId.name();
		}
		return null;
	}

	@Nullable
	public String getBranch() throws IOException {
		String name = getFullBranch();
		if (name != null)
			return shortenRefName(name);
		return null;
	}

	@NonNull
	public Set<ObjectId> getAdditionalHaves() {
		return Collections.emptySet();
	}

	@Nullable
	public final Ref exactRef(String name) throws IOException {
		return getRefDatabase().exactRef(name);
	}

	@Nullable
	public final Ref findRef(String name) throws IOException {
		return getRefDatabase().findRef(name);
	}

	@Deprecated
	@NonNull
	public Map<String
		try {
			return getRefDatabase().getRefs(RefDatabase.ALL);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Deprecated
	@NonNull
	public Map<String
		try {
			return getRefDatabase().getRefs(Constants.R_TAGS);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Deprecated
	@NonNull
	public Ref peel(Ref ref) {
		try {
			return getRefDatabase().peel(ref);
		} catch (IOException e) {
			return ref;
		}
	}

	@NonNull
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
					oset = new HashSet<>(oset);
				}
				ret.put(target
				oset.add(ref);
			}
		}
		return ret;
	}

	@NonNull
	public File getIndexFile() throws NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		return indexFile;
	}

	public RevCommit parseCommit(AnyObjectId id) throws IncorrectObjectTypeException
			IOException
		if (id instanceof RevCommit && ((RevCommit) id).getRawBuffer() != null) {
			return (RevCommit) id;
		}
		try (RevWalk walk = new RevWalk(this)) {
			return walk.parseCommit(id);
		}
	}

	@NonNull
	public DirCache readDirCache() throws NoWorkTreeException
			CorruptObjectException
		return DirCache.read(this);
	}

	@NonNull
	public DirCache lockDirCache() throws NoWorkTreeException
			CorruptObjectException
		IndexChangedListener l = new IndexChangedListener() {
			@Override
			public void onIndexChanged(IndexChangedEvent event) {
				notifyIndexChanged(true);
			}
		};
		return DirCache.lock(this
	}

	@NonNull
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
				throw new UncheckedIOException(e);
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
				throw new UncheckedIOException(e);
			}

			return RepositoryState.CHERRY_PICKING;
		}

		if (new File(getDirectory()
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.REVERTING_RESOLVED;
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}

			return RepositoryState.REVERTING;
		}

		return RepositoryState.SAFE;
	}

	public static boolean isValidRefName(String refName) {
		final int len = refName.length();
		if (len == 0) {
			return false;
		}
		if (refName.endsWith(LOCK_SUFFIX)) {
			return false;
		}

		try {
			SystemReader.getInstance().checkPath(refName);
		} catch (CorruptObjectException e) {
			return false;
		}

		int components = 1;
		char p = '\0';
		for (int i = 0; i < len; i++) {
			final char c = refName.charAt(i);
			if (c <= ' ')
				return false;
			switch (c) {
			case '.':
				switch (p) {
				case '\0': case '/': case '.':
					return false;
				}
				if (i == len -1)
					return false;
				break;
			case '/':
				if (i == 0 || i == len - 1)
					return false;
				if (p == '/')
					return false;
				components++;
				break;
			case '{':
				if (p == '@')
					return false;
				break;
			case '~': case '^': case ':':
			case '?': case '[': case '*':
			case '\\':
			case '\u007F':
				return false;
			}
			p = c;
		}
		return components > 1;
	}

	public static String normalizeBranchName(String name) {
		if (name == null || name.isEmpty()) {
		}
		String result = name.trim();
		String fullName = result.startsWith(Constants.R_HEADS) ? result
				: Constants.R_HEADS + result;
		if (isValidRefName(fullName)) {
			return result;
		}

		result = result.replaceAll("(?:\\h|\\v)+"
		StringBuilder b = new StringBuilder();
		char p = '/';
		for (int i = 0
			char c = result.charAt(i);
			if (c < ' ' || c == 127) {
				continue;
			}
			switch (c) {
			case '\\':
			case '^':
			case '~':
			case ':':
			case '?':
			case '*':
			case '[':
			case '@':
			case '<':
			case '>':
			case '|':
			case '"':
				c = '-';
				break;
			default:
				break;
			}
			switch (c) {
			case '/':
				if (p == '/') {
					continue;
				}
				p = '/';
				break;
			case '.':
			case '_':
			case '-':
				if (p == '/' || p == '-') {
					continue;
				}
				p = '-';
				break;
			default:
				p = c;
				break;
			}
			b.append(c);
		}
		result = b.toString().replaceFirst("[/_.-]+$"
				.replaceAll("\\.lock($|/)"
		return FORBIDDEN_BRANCH_NAME_COMPONENTS.matcher(result)
	}

	@NonNull
	public static String stripWorkDir(File workDir
		final String filePath = file.getPath();
		final String workDirPath = workDir.getPath();

		if (filePath.length() <= workDirPath.length() ||
		    filePath.charAt(workDirPath.length()) != File.separatorChar ||
		    !filePath.startsWith(workDirPath)) {
			File absWd = workDir.isAbsolute() ? workDir : workDir.getAbsoluteFile();
			File absFile = file.isAbsolute() ? file : file.getAbsoluteFile();
			if (absWd == workDir && absFile == file)
			return stripWorkDir(absWd
		}

		String relName = filePath.substring(workDirPath.length() + 1);
		if (File.separatorChar != '/')
			relName = relName.replace(File.separatorChar
		return relName;
	}

	public boolean isBare() {
		return workTree == null;
	}

	@NonNull
	public File getWorkTree() throws NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		return workTree;
	}

	public abstract void scanForRepoChanges() throws IOException;

	public abstract void notifyIndexChanged(boolean internal);

	@NonNull
	public static String shortenRefName(String refName) {
		if (refName.startsWith(Constants.R_HEADS))
			return refName.substring(Constants.R_HEADS.length());
		if (refName.startsWith(Constants.R_TAGS))
			return refName.substring(Constants.R_TAGS.length());
		if (refName.startsWith(Constants.R_REMOTES))
			return refName.substring(Constants.R_REMOTES.length());
		return refName;
	}

	@Nullable
	public String shortenRemoteBranchName(String refName) {
		for (String remote : getRemoteNames()) {
			if (refName.startsWith(remotePrefix))
				return refName.substring(remotePrefix.length());
		}
		return null;
	}

	@Nullable
	public String getRemoteName(String refName) {
		for (String remote : getRemoteNames()) {
			if (refName.startsWith(remotePrefix))
				return remote;
		}
		return null;
	}

	@Nullable
	public String getGitwebDescription() throws IOException {
		return null;
	}

	public void setGitwebDescription(@Nullable String description)
			throws IOException {
		throw new IOException(JGitText.get().unsupportedRepositoryDescription);
	}

	@Nullable
	public abstract ReflogReader getReflogReader(String refName)
			throws IOException;

	@Nullable
	public String readMergeCommitMsg() throws IOException
		return readCommitMsgFile(Constants.MERGE_MSG);
	}

	public void writeMergeCommitMsg(String msg) throws IOException {
		File mergeMsgFile = new File(gitDir
		writeCommitMsg(mergeMsgFile
	}

	@Nullable
	public String readCommitEditMsg() throws IOException
		return readCommitMsgFile(Constants.COMMIT_EDITMSG);
	}

	public void writeCommitEditMsg(String msg) throws IOException {
		File commiEditMsgFile = new File(gitDir
		writeCommitMsg(commiEditMsgFile
	}

	@Nullable
	public List<ObjectId> readMergeHeads() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.MERGE_HEAD);
		if (raw == null)
			return null;

		LinkedList<ObjectId> heads = new LinkedList<>();
		for (int p = 0; p < raw.length;) {
			heads.add(ObjectId.fromString(raw
			p = RawParseUtils
					.nextLF(raw
		}
		return heads;
	}

	public void writeMergeHeads(List<? extends ObjectId> heads) throws IOException {
		writeHeadsFile(heads
	}

	@Nullable
	public ObjectId readCherryPickHead() throws IOException
			NoWorkTreeException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.CHERRY_PICK_HEAD);
		if (raw == null)
			return null;

		return ObjectId.fromString(raw
	}

	@Nullable
	public ObjectId readRevertHead() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.REVERT_HEAD);
		if (raw == null)
			return null;
		return ObjectId.fromString(raw
	}

	public void writeCherryPickHead(ObjectId head) throws IOException {
		List<ObjectId> heads = (head != null) ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	public void writeRevertHead(ObjectId head) throws IOException {
		List<ObjectId> heads = (head != null) ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	public void writeOrigHead(ObjectId head) throws IOException {
		List<ObjectId> heads = head != null ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	@Nullable
	public ObjectId readOrigHead() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.ORIG_HEAD);
		return raw != null ? ObjectId.fromString(raw
	}

	@Nullable
	public String readSquashCommitMsg() throws IOException {
		return readCommitMsgFile(Constants.SQUASH_MSG);
	}

	public void writeSquashCommitMsg(String msg) throws IOException {
		File squashMsgFile = new File(gitDir
		writeCommitMsg(squashMsgFile
	}

	@Nullable
	private String readCommitMsgFile(String msgFilename) throws IOException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeMsgFile = new File(getDirectory()
		try {
			return RawParseUtils.decode(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			if (mergeMsgFile.exists()) {
				throw e;
			}
			return null;
		}
	}

	private void writeCommitMsg(File msgFile
		if (msg != null) {
			try (FileOutputStream fos = new FileOutputStream(msgFile)) {
				fos.write(msg.getBytes(UTF_8));
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
			if (file.exists()) {
				throw notFound;
			}
			return null;
		}
	}

	private void writeHeadsFile(List<? extends ObjectId> heads
			throws FileNotFoundException
		File headsFile = new File(getDirectory()
		if (heads != null) {
			try (OutputStream bos = new BufferedOutputStream(
					new FileOutputStream(headsFile))) {
				for (ObjectId id : heads) {
					id.copyTo(bos);
					bos.write('\n');
				}
			}
		} else {
			FileUtils.delete(headsFile
		}
	}

	@NonNull
	public List<RebaseTodoLine> readRebaseTodo(String path
			boolean includeComments)
			throws IOException {
		return new RebaseTodoFile(this).readRebaseTodo(path
	}

	public void writeRebaseTodoFile(String path
			boolean append)
			throws IOException {
		new RebaseTodoFile(this).writeRebaseTodoFile(path
	}

	@NonNull
	public Set<String> getRemoteNames() {
		return getConfig()
				.getSubsections(ConfigConstants.CONFIG_REMOTE_SECTION);
	}

	public void autoGC(ProgressMonitor monitor) {
	}
}
