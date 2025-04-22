
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.MissingBundlePrerequisiteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

class BundleFetchConnection extends BaseFetchConnection {

	private final Transport transport;

	InputStream bin;

	final Map<ObjectId

	private String lockMessage;

	private PackLock packLock;

	BundleFetchConnection(Transport transportBundle
		transport = transportBundle;
		bin = new BufferedInputStream(src);
		try {
			switch (readSignature()) {
			case 2:
				readBundleV2();
				break;
			default:
				throw new TransportException(transport.uri
			}
		} catch (TransportException err) {
			close();
			throw err;
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(transport.uri
		}
	}

	private int readSignature() throws IOException {
		final String rev = readLine(new byte[1024]);
		if (TransportBundle.V2_BUNDLE_SIGNATURE.equals(rev))
			return 2;
		throw new TransportException(transport.uri
	}

	private void readBundleV2() throws IOException {
		final byte[] hdrbuf = new byte[1024];
		final LinkedHashMap<String
		for (;;) {
			String line = readLine(hdrbuf);
			if (line.length() == 0)
				break;

			if (line.charAt(0) == '-') {
				ObjectId id = ObjectId.fromString(line.substring(1
				String shortDesc = null;
				if (line.length() > 42)
					shortDesc = line.substring(42);
				prereqs.put(id
				continue;
			}

			final String name = line.substring(41
			final ObjectId id = ObjectId.fromString(line.substring(0
			final Ref prior = avail.put(name
					Ref.Storage.NETWORK
			if (prior != null)
				throw duplicateAdvertisement(name);
		}
		available(avail);
	}

	private PackProtocolException duplicateAdvertisement(String name) {
		return new PackProtocolException(transport.uri
				MessageFormat.format(JGitText.get().duplicateAdvertisementsOf
	}

	private String readLine(byte[] hdrbuf) throws IOException {
		StringBuilder line = new StringBuilder();
		boolean done = false;
		while (!done) {
			bin.mark(hdrbuf.length);
			final int cnt = bin.read(hdrbuf);
			if (cnt < 0) {
				throw new EOFException(JGitText.get().shortReadOfBlock);
			}
			int lf = 0;
			while (lf < cnt && hdrbuf[lf] != '\n') {
				lf++;
			}
			bin.reset();
			IO.skipFully(bin
			if (lf < cnt && hdrbuf[lf] == '\n') {
				IO.skipFully(bin
				done = true;
			}
			line.append(RawParseUtils.decode(UTF_8
		}
		return line.toString();
	}

	@Override
	public boolean didFetchTestConnectivity() {
		return false;
	}

	@Override
	protected void doFetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException {
		verifyPrerequisites();
		try {
			try (ObjectInserter ins = transport.local.newObjectInserter()) {
				PackParser parser = ins.newPackParser(bin);
				parser.setAllowThin(true);
				parser.setObjectChecker(transport.getObjectChecker());
				parser.setLockMessage(lockMessage);
				packLock = parser.parse(NullProgressMonitor.INSTANCE);
				ins.flush();
			}
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(transport.uri
		}
	}

	@Override
	public void setPackLockMessage(String message) {
		lockMessage = message;
	}

	@Override
	public Collection<PackLock> getPackLocks() {
		if (packLock != null)
			return Collections.singleton(packLock);
		return Collections.<PackLock> emptyList();
	}

	private void verifyPrerequisites() throws TransportException {
		if (prereqs.isEmpty())
			return;

		try (RevWalk rw = new RevWalk(transport.local)) {

			final Map<ObjectId
			final List<RevObject> commits = new ArrayList<>();
			for (Map.Entry<ObjectId
				ObjectId p = e.getKey();
				try {
					final RevCommit c = rw.parseCommit(p);
					if (!c.has(PREREQ)) {
						c.add(PREREQ);
						commits.add(c);
					}
				} catch (MissingObjectException notFound) {
					missing.put(p
				} catch (IOException err) {
					throw new TransportException(transport.uri
							.format(JGitText.get().cannotReadCommit
							err);
				}
			}
			if (!missing.isEmpty())
				throw new MissingBundlePrerequisiteException(transport.uri
						missing);

			List<Ref> localRefs;
			try {
				localRefs = transport.local.getRefDatabase().getRefs();
			} catch (IOException e) {
				throw new TransportException(transport.uri
			}
			for (Ref r : localRefs) {
				try {
					rw.markStart(rw.parseCommit(r.getObjectId()));
				} catch (IOException readError) {
				}
			}

			int remaining = commits.size();
			try {
				RevCommit c;
				while ((c = rw.next()) != null) {
					if (c.has(PREREQ)) {
						c.add(SEEN);
						if (--remaining == 0)
							break;
					}
				}
			} catch (IOException err) {
				throw new TransportException(transport.uri
						JGitText.get().cannotReadObject
			}

			if (remaining > 0) {
				for (RevObject o : commits) {
					if (!o.has(SEEN))
						missing.put(o
				}
				throw new MissingBundlePrerequisiteException(transport.uri
						missing);
			}
		}
	}

	@Override
	public void close() {
		if (bin != null) {
			try {
				bin.close();
			} catch (IOException ie) {
			} finally {
				bin = null;
			}
		}
	}
}
