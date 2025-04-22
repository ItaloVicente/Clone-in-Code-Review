
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.attributes.AttributesHandler;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
import org.eclipse.jgit.attributes.AttributesProvider;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.QuotedString;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;

public class TreeWalk implements AutoCloseable
	private static final AbstractTreeIterator[] NO_TREES = {};

	public static enum OperationType {
		CHECKOUT_OP

		CHECKIN_OP
	}

	private OperationType operationType = OperationType.CHECKOUT_OP;

	private Map<String

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public static TreeWalk forPath(final ObjectReader reader
			final AnyObjectId... trees) throws MissingObjectException
			IncorrectObjectTypeException
		return forPath(null
	}

	public static TreeWalk forPath(final @Nullable Repository repo
			final ObjectReader reader
			final AnyObjectId... trees)
					throws MissingObjectException
					CorruptObjectException
		TreeWalk tw = new TreeWalk(repo
		PathFilter f = PathFilter.create(path);
		tw.setFilter(f);
		tw.reset(trees);
		tw.setRecursive(false);

		while (tw.next()) {
			if (f.isDone(tw)) {
				return tw;
			} else if (tw.isSubtree()) {
				tw.enterSubtree();
			}
		}
		return null;
	}

	public static TreeWalk forPath(final Repository db
			final AnyObjectId... trees) throws MissingObjectException
			IncorrectObjectTypeException
		try (ObjectReader reader = db.newObjectReader()) {
			return forPath(db
		}
	}

	public static TreeWalk forPath(final Repository db
			final RevTree tree) throws MissingObjectException
			IncorrectObjectTypeException
		return forPath(db
	}

	private final ObjectReader reader;

	private final boolean closeReader;

	private final MutableObjectId idBuffer = new MutableObjectId();

	private TreeFilter filter;

	AbstractTreeIterator[] trees;

	private boolean recursive;

	private boolean postOrderTraversal;

	int depth;

	private boolean advance;

	private boolean postChildren;

	private AttributesNodeProvider attributesNodeProvider;

	AbstractTreeIterator currentHead;

	private Attributes attrs = null;

	private AttributesHandler attributesHandler;

	private Config config;

	private Set<String> filterCommands;

	public TreeWalk(Repository repo) {
		this(repo
	}

	public TreeWalk(@Nullable Repository repo
		this(repo
	}

	public TreeWalk(ObjectReader or) {
		this(null
	}

	private TreeWalk(final @Nullable Repository repo
			final boolean closeReader) {
		if (repo != null) {
			config = repo.getConfig();
			attributesNodeProvider = repo.createAttributesNodeProvider();
			filterCommands = FilterCommandRegistry
					.getRegisteredFilterCommands();
		} else {
			config = null;
			attributesNodeProvider = null;
		}
		reader = or;
		filter = TreeFilter.ALL;
		trees = NO_TREES;
		this.closeReader = closeReader;
	}

	public ObjectReader getObjectReader() {
		return reader;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	@Override
	public void close() {
		if (closeReader) {
			reader.close();
		}
	}

	public TreeFilter getFilter() {
		return filter;
	}

	public void setFilter(TreeFilter newFilter) {
		filter = newFilter != null ? newFilter : TreeFilter.ALL;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean b) {
		recursive = b;
	}

	public boolean isPostOrderTraversal() {
		return postOrderTraversal;
	}

	public void setPostOrderTraversal(boolean b) {
		postOrderTraversal = b;
	}

	public void setAttributesNodeProvider(AttributesNodeProvider provider) {
		attributesNodeProvider = provider;
	}

	public AttributesNodeProvider getAttributesNodeProvider() {
		return attributesNodeProvider;
	}

	@Override
	public Attributes getAttributes() {
		if (attrs != null)
			return attrs;

		if (attributesNodeProvider == null) {
			throw new IllegalStateException(
		}

		try {
			if (attributesHandler == null) {
				attributesHandler = new AttributesHandler(this);
			}
			attrs = attributesHandler.getAttributes();
			return attrs;
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes"
					e);
		}
	}

	@Nullable
	public EolStreamType getEolStreamType(OperationType opType) {
		if (attributesNodeProvider == null || config == null)
			return null;
		return EolStreamTypeUtil.detectStreamType(
				opType != null ? opType : operationType
					config.get(WorkingTreeOptions.KEY)
	}

	public void reset() {
		attrs = null;
		attributesHandler = null;
		trees = NO_TREES;
		advance = false;
		depth = 0;
	}

	public void reset(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		if (trees.length == 1) {
			AbstractTreeIterator o = trees[0];
			while (o.parent != null)
				o = o.parent;
			if (o instanceof CanonicalTreeParser) {
				o.matches = null;
				o.matchShift = 0;
				((CanonicalTreeParser) o).reset(reader
				trees[0] = o;
			} else {
				trees[0] = parserFor(id);
			}
		} else {
			trees = new AbstractTreeIterator[] { parserFor(id) };
		}

		advance = false;
		depth = 0;
		attrs = null;
	}

	public void reset(AnyObjectId... ids) throws MissingObjectException
			IncorrectObjectTypeException
		final int oldLen = trees.length;
		final int newLen = ids.length;
		final AbstractTreeIterator[] r = newLen == oldLen ? trees
				: new AbstractTreeIterator[newLen];
		for (int i = 0; i < newLen; i++) {
			AbstractTreeIterator o;

			if (i < oldLen) {
				o = trees[i];
				while (o.parent != null)
					o = o.parent;
				if (o instanceof CanonicalTreeParser && o.pathOffset == 0) {
					o.matches = null;
					o.matchShift = 0;
					((CanonicalTreeParser) o).reset(reader
					r[i] = o;
					continue;
				}
			}

			o = parserFor(ids[i]);
			r[i] = o;
		}

		trees = r;
		advance = false;
		depth = 0;
		attrs = null;
	}

	public int addTree(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		return addTree(parserFor(id));
	}

	public int addTree(AbstractTreeIterator p) {
		int n = trees.length;
		AbstractTreeIterator[] newTrees = new AbstractTreeIterator[n + 1];

		System.arraycopy(trees
		newTrees[n] = p;
		p.matches = null;
		p.matchShift = 0;

		trees = newTrees;
		return n;
	}

	public int getTreeCount() {
		return trees.length;
	}

	public boolean next() throws MissingObjectException
			IncorrectObjectTypeException
		try {
			if (advance) {
				advance = false;
				postChildren = false;
				popEntriesEqual();
			}

			for (;;) {
				attrs = null;
				final AbstractTreeIterator t = min();
				if (t.eof()) {
					if (depth > 0) {
						exitSubtree();
						if (postOrderTraversal) {
							advance = true;
							postChildren = true;
							return true;
						}
						popEntriesEqual();
						continue;
					}
					return false;
				}

				currentHead = t;
				if (filter.matchFilter(this) == 1) {
					skipEntriesEqual();
					continue;
				}

				if (recursive && FileMode.TREE.equals(t.mode)) {
					enterSubtree();
					continue;
				}

				advance = true;
				return true;
			}
		} catch (StopWalkException stop) {
			stopWalk();
			return false;
		}
	}

	void stopWalk() throws IOException {
		for (AbstractTreeIterator t : trees) {
			t.stopWalk();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractTreeIterator> T getTree(final int nth
			final Class<T> clazz) {
		final AbstractTreeIterator t = trees[nth];
		return t.matches == currentHead ? (T) t : null;
	}

	public int getRawMode(int nth) {
		final AbstractTreeIterator t = trees[nth];
		return t.matches == currentHead ? t.mode : 0;
	}

	public FileMode getFileMode(int nth) {
		return FileMode.fromBits(getRawMode(nth));
	}

	public FileMode getFileMode() {
		return FileMode.fromBits(currentHead.mode);
	}

	public ObjectId getObjectId(int nth) {
		final AbstractTreeIterator t = trees[nth];
		return t.matches == currentHead ? t.getEntryObjectId() : ObjectId
				.zeroId();
	}

	public void getObjectId(MutableObjectId out
		final AbstractTreeIterator t = trees[nth];
		if (t.matches == currentHead)
			t.getEntryObjectId(out);
		else
			out.clear();
	}

	public boolean idEqual(int nthA
		final AbstractTreeIterator ch = currentHead;
		final AbstractTreeIterator a = trees[nthA];
		final AbstractTreeIterator b = trees[nthB];
		if (a.matches != ch && b.matches != ch) {
			return true;
		}
		if (!a.hasId() || !b.hasId())
			return false;
		if (a.matches == ch && b.matches == ch)
			return a.idEqual(b);
		return false;
	}

	public String getNameString() {
		final AbstractTreeIterator t = currentHead;
		final int off = t.pathOffset;
		final int end = t.pathLen;
		return RawParseUtils.decode(UTF_8
	}

	public String getPathString() {
		return pathOf(currentHead);
	}

	public byte[] getRawPath() {
		final AbstractTreeIterator t = currentHead;
		final int n = t.pathLen;
		final byte[] r = new byte[n];
		System.arraycopy(t.path
		return r;
	}

	public int getPathLength() {
		return currentHead.pathLen;
	}

	public int isPathMatch(byte[] p
		final AbstractTreeIterator t = currentHead;
		final byte[] c = t.path;
		final int cLen = t.pathLen;
		int ci;

		for (ci = 0; ci < cLen && ci < pLen; ci++) {
			final int c_value = (c[ci] & 0xff) - (p[ci] & 0xff);
			if (c_value != 0) {
				return 1;
			}
		}

		if (ci < cLen) {
			return c[ci] == '/' ? 0 : 1;
		}

		if (ci < pLen) {
			return p[ci] == '/' && FileMode.TREE.equals(t.mode) ? -1 : 1;
		}

		return 0;
	}

	public int isPathPrefix(byte[] p
		final AbstractTreeIterator t = currentHead;
		final byte[] c = t.path;
		final int cLen = t.pathLen;
		int ci;

		for (ci = 0; ci < cLen && ci < pLen; ci++) {
			final int c_value = (c[ci] & 0xff) - (p[ci] & 0xff);
			if (c_value != 0)
				return c_value;
		}

		if (ci < cLen) {
			return c[ci] == '/' ? 0 : -1;
		}

		if (ci < pLen) {
			return p[ci] == '/' && FileMode.TREE.equals(t.mode) ? 0 : -1;
		}

		return 0;
	}

	public boolean isPathSuffix(byte[] p
		final AbstractTreeIterator t = currentHead;
		final byte[] c = t.path;
		final int cLen = t.pathLen;

		for (int i = 1; i <= pLen; i++) {
			if (i > cLen)
				return false;
			if (c[cLen - i] != p[pLen - i])
				return false;
		}

		return true;
	}

	public int getDepth() {
		return depth;
	}

	public boolean isSubtree() {
		return FileMode.TREE.equals(currentHead.mode);
	}

	public boolean isPostChildren() {
		return postChildren && isSubtree();
	}

	public void enterSubtree() throws MissingObjectException
			IncorrectObjectTypeException
		attrs = null;
		final AbstractTreeIterator ch = currentHead;
		final AbstractTreeIterator[] tmp = new AbstractTreeIterator[trees.length];
		for (int i = 0; i < trees.length; i++) {
			final AbstractTreeIterator t = trees[i];
			final AbstractTreeIterator n;
			if (t.matches == ch && !t.eof() &&
					(FileMode.TREE.equals(t.mode)
							|| (FileMode.GITLINK.equals(t.mode) && t.isWorkTree())))
				n = t.createSubtreeIterator(reader
			else
				n = t.createEmptyTreeIterator();
			tmp[i] = n;
		}
		depth++;
		advance = false;
		System.arraycopy(tmp
	}

	@SuppressWarnings("unused")
	AbstractTreeIterator min() throws CorruptObjectException {
		int i = 0;
		AbstractTreeIterator minRef = trees[i];
		while (minRef.eof() && ++i < trees.length)
			minRef = trees[i];
		if (minRef.eof())
			return minRef;

		minRef.matches = minRef;
		while (++i < trees.length) {
			final AbstractTreeIterator t = trees[i];
			if (t.eof())
				continue;
			final int cmp = t.pathCompare(minRef);
			if (cmp < 0) {
				t.matches = t;
				minRef = t;
			} else if (cmp == 0) {
				t.matches = minRef;
			}
		}

		return minRef;
	}

	void popEntriesEqual() throws CorruptObjectException {
		final AbstractTreeIterator ch = currentHead;
            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    t.next(1);
                    t.matches = null;
                }
            }
	}

	void skipEntriesEqual() throws CorruptObjectException {
		final AbstractTreeIterator ch = currentHead;
            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    t.skip();
                    t.matches = null;
                }
            }
	}

	void exitSubtree() {
		depth--;
		for (int i = 0; i < trees.length; i++)
			trees[i] = trees[i].parent;

		AbstractTreeIterator minRef = null;
		for (AbstractTreeIterator t : trees) {
			if (t.matches != t)
				continue;
			if (minRef == null || t.pathCompare(minRef) < 0)
				minRef = t;
		}
		currentHead = minRef;
	}

	private CanonicalTreeParser parserFor(AnyObjectId id)
			throws IncorrectObjectTypeException
		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(reader
		return p;
	}

	static String pathOf(AbstractTreeIterator t) {
		return RawParseUtils.decode(UTF_8
	}

	static String pathOf(byte[] buf
		return RawParseUtils.decode(UTF_8
	}

	public <T extends AbstractTreeIterator> T getTree(
			Class<T> type) {
            for (AbstractTreeIterator tree : trees) {
                if (type.isInstance(tree)) {
                    return type.cast(tree);
                }
            }
		return null;
	}

	public String getFilterCommand(String filterCommandType)
			throws IOException {
		Attributes attributes = getAttributes();

		Attribute f = attributes.get(Constants.ATTR_FILTER);
		if (f == null) {
			return null;
		}
		String filterValue = f.getValue();
		if (filterValue == null) {
			return null;
		}

		String filterCommand = getFilterCommandDefinition(filterValue
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				Matcher.quoteReplacement(
						QuotedString.BOURNE.quote((getPathString()))));
	}

	private String getFilterCommandDefinition(String filterDriverName
			String filterCommandType) {
		String filterCommand = filterCommandsByNameDotType.get(key);
		if (filterCommand != null)
			return filterCommand;
		filterCommand = config.getString(ConfigConstants.CONFIG_FILTER_SECTION
				filterDriverName
		boolean useBuiltin = config.getBoolean(
				ConfigConstants.CONFIG_FILTER_SECTION
				filterDriverName
		if (useBuiltin) {
			String builtinFilterCommand = Constants.BUILTIN_FILTER_PREFIX
					+ filterDriverName + '/' + filterCommandType;
			if (filterCommands != null
					&& filterCommands.contains(builtinFilterCommand)) {
				filterCommand = builtinFilterCommand;
			}
		}
		if (filterCommand != null) {
			filterCommandsByNameDotType.put(key
		}
		return filterCommand;
	}
}
