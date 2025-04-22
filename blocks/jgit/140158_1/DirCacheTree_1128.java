
package org.eclipse.jgit.dircache;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesRule;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.RawParseUtils;

public class DirCacheIterator extends AbstractTreeIterator {
	private static final byte[] DOT_GIT_ATTRIBUTES_BYTES = Constants.DOT_GIT_ATTRIBUTES
			.getBytes(UTF_8);

	protected final DirCache cache;

	private final DirCacheTree tree;

	private final int treeStart;

	private final int treeEnd;

	private final byte[] subtreeId;

	protected int ptr;

	private int nextSubtreePos;

	protected DirCacheEntry currentEntry;

	protected DirCacheTree currentSubtree;

	public DirCacheIterator(DirCache dc) {
		cache = dc;
		tree = dc.getCacheTree(true);
		treeStart = 0;
		treeEnd = tree.getEntrySpan();
		subtreeId = new byte[Constants.OBJECT_ID_LENGTH];
		if (!eof())
			parseEntry();
	}

	DirCacheIterator(DirCacheIterator p
		super(p
		cache = p.cache;
		tree = dct;
		treeStart = p.ptr;
		treeEnd = treeStart + tree.getEntrySpan();
		subtreeId = p.subtreeId;
		ptr = p.ptr;
		parseEntry();
	}

	@Override
	public AbstractTreeIterator createSubtreeIterator(ObjectReader reader)
			throws IncorrectObjectTypeException
		if (currentSubtree == null)
			throw new IncorrectObjectTypeException(getEntryObjectId()
					Constants.TYPE_TREE);
		return new DirCacheIterator(this
	}

	@Override
	public EmptyTreeIterator createEmptyTreeIterator() {
		final byte[] n = new byte[Math.max(pathLen + 1
		System.arraycopy(path
		n[pathLen] = '/';
		return new EmptyTreeIterator(this
	}

	@Override
	public boolean hasId() {
		if (currentSubtree != null)
			return currentSubtree.isValid();
		return currentEntry != null;
	}

	@Override
	public byte[] idBuffer() {
		if (currentSubtree != null)
			return currentSubtree.isValid() ? subtreeId : zeroid;
		if (currentEntry != null)
			return currentEntry.idBuffer();
		return zeroid;
	}

	@Override
	public int idOffset() {
		if (currentSubtree != null)
			return 0;
		if (currentEntry != null)
			return currentEntry.idOffset();
		return 0;
	}

	@Override
	public void reset() {
		if (!first()) {
			ptr = treeStart;
			nextSubtreePos = 0;
			currentEntry = null;
			currentSubtree = null;
			if (!eof())
				parseEntry();
		}
	}

	@Override
	public boolean first() {
		return ptr == treeStart;
	}

	@Override
	public boolean eof() {
		return ptr == treeEnd;
	}

	@Override
	public void next(int delta) {
		while (--delta >= 0) {
			if (currentSubtree != null)
				ptr += currentSubtree.getEntrySpan();
			else
				ptr++;
			if (eof())
				break;
			parseEntry();
		}
	}

	@Override
	public void back(int delta) {
		while (--delta >= 0) {
			if (currentSubtree != null)
				nextSubtreePos--;
			ptr--;
			parseEntry(false);
			if (currentSubtree != null)
				ptr -= currentSubtree.getEntrySpan() - 1;
		}
	}

	private void parseEntry() {
		parseEntry(true);
	}

	private void parseEntry(boolean forward) {
		currentEntry = cache.getEntry(ptr);
		final byte[] cep = currentEntry.path;

		if (!forward) {
			if (nextSubtreePos > 0) {
				final DirCacheTree p = tree.getChild(nextSubtreePos - 1);
				if (p.contains(cep
					nextSubtreePos--;
					currentSubtree = p;
				}
			}
		}
		if (nextSubtreePos != tree.getChildCount()) {
			final DirCacheTree s = tree.getChild(nextSubtreePos);
			if (s.contains(cep
				currentSubtree = s;
				nextSubtreePos++;

				if (s.isValid())
					s.getObjectId().copyRawTo(subtreeId
				mode = FileMode.TREE.getBits();
				path = cep;
				pathLen = pathOffset + s.nameLength();
				return;
			}
		}

		mode = currentEntry.getRawMode();
		path = cep;
		pathLen = cep.length;
		currentSubtree = null;
		if (RawParseUtils.match(path
			attributesNode = new LazyLoadingAttributesNode(
					currentEntry.getObjectId());
	}

	public DirCacheEntry getDirCacheEntry() {
		return currentSubtree == null ? currentEntry : null;
	}

	public AttributesNode getEntryAttributesNode(ObjectReader reader)
			throws IOException {
		if (attributesNode instanceof LazyLoadingAttributesNode)
			attributesNode = ((LazyLoadingAttributesNode) attributesNode)
					.load(reader);
		return attributesNode;
	}

	private static class LazyLoadingAttributesNode extends AttributesNode {
		final ObjectId objectId;

		LazyLoadingAttributesNode(ObjectId objectId) {
			super(Collections.<AttributesRule> emptyList());
			this.objectId = objectId;

		}

		AttributesNode load(ObjectReader reader) throws IOException {
			AttributesNode r = new AttributesNode();
			ObjectLoader loader = reader.open(objectId);
			if (loader != null) {
				try (InputStream in = loader.openStream()) {
					r.parse(in);
				}
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

}
