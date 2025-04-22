
package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;

public class RevCommit extends RevObject {
	private static final int STACK_DEPTH = 500;

	public static RevCommit parse(byte[] raw) {
		try {
			return parse(new RevWalk((ObjectReader) null)
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static RevCommit parse(RevWalk rw
		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			RevCommit r = rw.lookupCommit(fmt.idFor(Constants.OBJ_COMMIT
			r.parseCanonical(rw
			r.buffer = raw;
			return r;
		}
	}

	static final RevCommit[] NO_PARENTS = {};

	private RevTree tree;

	RevCommit[] parents;


	int inDegree;

	private byte[] buffer;

	protected RevCommit(AnyObjectId id) {
		super(id);
	}

	@Override
	void parseHeaders(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		parseCanonical(walk
	}

	@Override
	void parseBody(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (buffer == null) {
			buffer = walk.getCachedBytes(this);
			if ((flags & PARSED) == 0)
				parseCanonical(walk
		}
	}

	void parseCanonical(RevWalk walk
		if (!walk.shallowCommitsInitialized) {
			walk.initializeShallowCommits(this);
		}

		final MutableObjectId idBuffer = walk.idBuffer;
		idBuffer.fromString(raw
		tree = walk.lookupTree(idBuffer);

		int ptr = 46;
		if (parents == null) {
			RevCommit[] pList = new RevCommit[1];
			int nParents = 0;
			for (;;) {
				if (raw[ptr] != 'p') {
					break;
				}
				idBuffer.fromString(raw
				final RevCommit p = walk.lookupCommit(idBuffer);
				if (nParents == 0) {
					pList[nParents++] = p;
				} else if (nParents == 1) {
					pList = new RevCommit[] { pList[0]
					nParents = 2;
				} else {
					if (pList.length <= nParents) {
						RevCommit[] old = pList;
						pList = new RevCommit[pList.length + 32];
						System.arraycopy(old
					}
					pList[nParents++] = p;
				}
				ptr += 48;
			}
			if (nParents != pList.length) {
				RevCommit[] old = pList;
				pList = new RevCommit[nParents];
				System.arraycopy(old
			}
			parents = pList;
		}

		ptr = RawParseUtils.committer(raw
		if (ptr > 0) {
			ptr = RawParseUtils.nextLF(raw

			commitTime = RawParseUtils.parseBase10(raw
		}

		if (walk.isRetainBody()) {
			buffer = raw;
		}
		flags |= PARSED;
	}

	@Override
	public final int getType() {
		return Constants.OBJ_COMMIT;
	}

	static void carryFlags(RevCommit c
		FIFORevQueue q = carryFlags1(c
		if (q != null)
			slowCarryFlags(q
	}

	private static FIFORevQueue carryFlags1(RevCommit c
		for(;;) {
			RevCommit[] pList = c.parents;
			if (pList == null || pList.length == 0)
				return null;
			if (pList.length != 1) {
				if (depth == STACK_DEPTH)
					return defer(c);
				for (int i = 1; i < pList.length; i++) {
					RevCommit p = pList[i];
					if ((p.flags & carry) == carry)
						continue;
					p.flags |= carry;
					FIFORevQueue q = carryFlags1(p
					if (q != null)
						return defer(q
				}
			}

			c = pList[0];
			if ((c.flags & carry) == carry)
				return null;
			c.flags |= carry;
		}
	}

	private static FIFORevQueue defer(RevCommit c) {
		FIFORevQueue q = new FIFORevQueue();
		q.add(c);
		return q;
	}

	private static FIFORevQueue defer(FIFORevQueue q
			RevCommit[] pList
		carryOneStep(q

		for (; i < pList.length; i++)
			carryOneStep(q
		return q;
	}

	private static void slowCarryFlags(FIFORevQueue q
		for (RevCommit c; (c = q.next()) != null;) {
			for (RevCommit p : c.parents)
				carryOneStep(q
		}
	}

	private static void carryOneStep(FIFORevQueue q
		if ((c.flags & carry) != carry) {
			c.flags |= carry;
			if (c.parents != null)
				q.add(c);
		}
	}

	public void carry(RevFlag flag) {
		final int carry = flags & flag.mask;
		if (carry != 0)
			carryFlags(this
	}

	public final int getCommitTime() {
		return commitTime;
	}

	public final RevTree getTree() {
		return tree;
	}

	public final int getParentCount() {
		return parents.length;
	}

	public final RevCommit getParent(int nth) {
		return parents[nth];
	}

	public final RevCommit[] getParents() {
		return parents;
	}

	public final byte[] getRawBuffer() {
		return buffer;
	}

	public final byte[] getRawGpgSignature() {
		final byte[] raw = buffer;
		final byte[] header = {'g'
		final int start = RawParseUtils.headerStart(header
		if (start < 0) {
			return null;
		}
		final int end = RawParseUtils.headerEnd(raw
		return Arrays.copyOfRange(raw
	}

	public final PersonIdent getAuthorIdent() {
		final byte[] raw = buffer;
		final int nameB = RawParseUtils.author(raw
		if (nameB < 0)
			return null;
		return RawParseUtils.parsePersonIdent(raw
	}

	public final PersonIdent getCommitterIdent() {
		final byte[] raw = buffer;
		final int nameB = RawParseUtils.committer(raw
		if (nameB < 0)
			return null;
		return RawParseUtils.parsePersonIdent(raw
	}

	public final String getFullMessage() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.commitMessage(raw
		if (msgB < 0) {
		}
		return RawParseUtils.decode(guessEncoding()
	}

	public final String getShortMessage() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.commitMessage(raw
		if (msgB < 0) {
		}

		int msgE = RawParseUtils.endOfParagraph(raw
		String str = RawParseUtils.decode(guessEncoding()
		if (hasLF(raw
			str = StringUtils.replaceLineBreaksWithSpace(str);
		}
		return str;
	}

	static boolean hasLF(byte[] r
		while (b < e)
			if (r[b++] == '\n')
				return true;
		return false;
	}

	@Nullable
	public final String getEncodingName() {
		return RawParseUtils.parseEncodingName(buffer);
	}

	public final Charset getEncoding() {
		return RawParseUtils.parseEncoding(buffer);
	}

	private Charset guessEncoding() {
		try {
			return getEncoding();
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			return UTF_8;
		}
	}

	public final List<FooterLine> getFooterLines() {
		final byte[] raw = buffer;
		int ptr = raw.length - 1;
			ptr--;

		final int msgB = RawParseUtils.commitMessage(raw
		final ArrayList<FooterLine> r = new ArrayList<>(4);
		final Charset enc = guessEncoding();
		for (;;) {
			ptr = RawParseUtils.prevLF(raw
			if (ptr <= msgB)

			final int keyStart = ptr + 2;
			if (raw[keyStart] == '\n')

			final int keyEnd = RawParseUtils.endOfFooterLineKey(raw
			if (keyEnd < 0)

			int valStart = keyEnd + 1;
			while (valStart < raw.length && raw[valStart] == ' ')
				valStart++;

			int valEnd = RawParseUtils.nextLF(raw
			if (raw[valEnd - 1] == '\n')
				valEnd--;

			r.add(new FooterLine(raw
		}
		Collections.reverse(r);
		return r;
	}

	public final List<String> getFooterLines(String keyName) {
		return getFooterLines(new FooterKey(keyName));
	}

	public final List<String> getFooterLines(FooterKey keyName) {
		final List<FooterLine> src = getFooterLines();
		if (src.isEmpty())
			return Collections.emptyList();
		final ArrayList<String> r = new ArrayList<>(src.size());
		for (FooterLine f : src) {
			if (f.matches(keyName))
				r.add(f.getValue());
		}
		return r;
	}

	public void reset() {
		inDegree = 0;
	}

	public final void disposeBody() {
		buffer = null;
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		s.append(Constants.typeString(getType()));
		s.append(' ');
		s.append(name());
		s.append(' ');
		s.append(commitTime);
		s.append(' ');
		appendCoreFlags(s);
		return s.toString();
	}
}
