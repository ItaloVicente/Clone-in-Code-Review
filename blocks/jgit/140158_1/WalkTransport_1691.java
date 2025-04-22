
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.IO;

abstract class WalkRemoteObjectDatabase {




	static final String INFO_REFS = ROOT_DIR + Constants.INFO_REFS;

	abstract URIish getURI();

	abstract Collection<String> getPackNames() throws IOException;

	abstract Collection<WalkRemoteObjectDatabase> getAlternates()
			throws IOException;

	abstract FileStream open(String path) throws FileNotFoundException
			IOException;

	abstract WalkRemoteObjectDatabase openAlternate(String location)
			throws IOException;

	abstract void close();

	void deleteFile(String path) throws IOException {
		throw new IOException(MessageFormat.format(JGitText.get().deletingNotSupported
	}

	OutputStream writeFile(final String path
			final String monitorTask) throws IOException {
		throw new IOException(MessageFormat.format(JGitText.get().writingNotSupported
	}

	void writeFile(String path
		try (OutputStream os = writeFile(path
			os.write(data);
		}
	}

	void deleteRef(String name) throws IOException {
		deleteFile(ROOT_DIR + name);
	}

	void deleteRefLog(String name) throws IOException {
	}

	void writeRef(String name
		final ByteArrayOutputStream b;

		b = new ByteArrayOutputStream(Constants.OBJECT_ID_STRING_LENGTH + 1);
		value.copyTo(b);
		b.write('\n');

		writeFile(ROOT_DIR + name
	}

	void writeInfoPacks(Collection<String> packNames) throws IOException {
		final StringBuilder w = new StringBuilder();
		for (String n : packNames) {
			w.append(n);
			w.append('\n');
		}
		writeFile(INFO_PACKS
	}

	BufferedReader openReader(String path) throws IOException {
		final InputStream is = open(path).in;
		return new BufferedReader(new InputStreamReader(is
	}

	Collection<WalkRemoteObjectDatabase> readAlternates(final String listPath)
			throws IOException {
		try (BufferedReader br = openReader(listPath)) {
			final Collection<WalkRemoteObjectDatabase> alts = new ArrayList<>();
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				alts.add(openAlternate(line));
			}
			return alts;
		}
	}

	protected void readPackedRefs(Map<String
			throws TransportException {
		try (BufferedReader br = openReader(ROOT_DIR + Constants.PACKED_REFS)) {
			readPackedRefsImpl(avail
		} catch (FileNotFoundException notPacked) {
		} catch (IOException e) {
			throw new TransportException(getURI()
		}
	}

	private void readPackedRefsImpl(final Map<String
			final BufferedReader br) throws IOException {
		Ref last = null;
		boolean peeled = false;
		for (;;) {
			String line = br.readLine();
			if (line == null)
				break;
			if (line.charAt(0) == '#') {
				if (line.startsWith(RefDirectory.PACKED_REFS_HEADER)) {
					line = line.substring(RefDirectory.PACKED_REFS_HEADER.length());
					peeled = line.contains(RefDirectory.PACKED_REFS_PEELED);
				}
				continue;
			}
			if (line.charAt(0) == '^') {
				if (last == null)
					throw new TransportException(JGitText.get().peeledLineBeforeRef);
				final ObjectId id = ObjectId.fromString(line.substring(1));
				last = new ObjectIdRef.PeeledTag(Ref.Storage.PACKED
						.getName()
				avail.put(last.getName()
				continue;
			}

			final int sp = line.indexOf(' ');
			if (sp < 0)
				throw new TransportException(MessageFormat.format(JGitText.get().unrecognizedRef
			final ObjectId id = ObjectId.fromString(line.substring(0
			final String name = line.substring(sp + 1);
			if (peeled)
				last = new ObjectIdRef.PeeledNonTag(Ref.Storage.PACKED
			else
				last = new ObjectIdRef.Unpeeled(Ref.Storage.PACKED
			avail.put(last.getName()
		}
	}

	static final class FileStream {
		final InputStream in;

		final long length;

		FileStream(InputStream i) {
			in = i;
			length = -1;
		}

		FileStream(InputStream i
			in = i;
			length = n;
		}

		byte[] toArray() throws IOException {
			try {
				if (length >= 0) {
					final byte[] r = new byte[(int) length];
					IO.readFully(in
					return r;
				}

				final ByteArrayOutputStream r = new ByteArrayOutputStream();
				final byte[] buf = new byte[2048];
				int n;
				while ((n = in.read(buf)) >= 0)
					r.write(buf
				return r.toByteArray();
			} finally {
				in.close();
			}
		}
	}
}
