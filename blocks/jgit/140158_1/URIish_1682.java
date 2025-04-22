
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.lib.Constants.LOCK_SUFFIX;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;

public class TransportSftp extends SshTransport implements WalkTransport {
	static final TransportProtocol PROTO_SFTP = new TransportProtocol() {
		@Override
		public String getName() {
			return JGitText.get().transportProtoSFTP;
		}

		@Override
		public Set<String> getSchemes() {
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		@Override
		public int getDefaultPort() {
			return 22;
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportSftp(local
		}
	};

	TransportSftp(Repository local
		super(local
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		final SftpObjectDB c = new SftpObjectDB(uri.getPath());
		final WalkFetchConnection r = new WalkFetchConnection(this
		r.available(c.readAdvertisedRefs());
		return r;
	}

	@Override
	public PushConnection openPush() throws TransportException {
		final SftpObjectDB c = new SftpObjectDB(uri.getPath());
		final WalkPushConnection r = new WalkPushConnection(this
		r.available(c.readAdvertisedRefs());
		return r;
	}

	FtpChannel newSftp() throws IOException {
		FtpChannel channel = getSession().getFtpChannel();
		channel.connect(getTimeout()
		return channel;
	}

	class SftpObjectDB extends WalkRemoteObjectDatabase {
		private final String objectsPath;

		private FtpChannel ftp;

		SftpObjectDB(String path) throws TransportException {
				path = path.substring(1);
				path = path.substring(2);
			try {
				ftp = newSftp();
				ftp.cd(path);
				objectsPath = ftp.pwd();
			} catch (FtpChannel.FtpException f) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotEnterObjectsPath
						f.getMessage())
			} catch (IOException ioe) {
				close();
				throw new TransportException(uri
			}
		}

		SftpObjectDB(SftpObjectDB parent
				throws TransportException {
			try {
				ftp = newSftp();
				ftp.cd(parent.objectsPath);
				ftp.cd(p);
				objectsPath = ftp.pwd();
			} catch (FtpChannel.FtpException f) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotEnterPathFromParent
						parent.objectsPath
			} catch (IOException ioe) {
				close();
				throw new TransportException(uri
			}
		}

		@Override
		URIish getURI() {
			return uri.setPath(objectsPath);
		}

		@Override
		Collection<WalkRemoteObjectDatabase> getAlternates() throws IOException {
			try {
				return readAlternates(INFO_ALTERNATES);
			} catch (FileNotFoundException err) {
				return null;
			}
		}

		@Override
		WalkRemoteObjectDatabase openAlternate(String location)
				throws IOException {
			return new SftpObjectDB(this
		}

		@Override
		Collection<String> getPackNames() throws IOException {
			final List<String> packs = new ArrayList<>();
			try {
				Set<String> files = list.stream()
						.map(FtpChannel.DirEntry::getFilename)
						.collect(Collectors.toSet());
				HashMap<String

				for (FtpChannel.DirEntry ent : list) {
					String n = ent.getFilename();
						continue;
					}
					String in = n.substring(0
					if (!files.contains(in)) {
						continue;
					}
					mtimes.put(n
					packs.add(n);
				}

				Collections.sort(packs
						(o1
			} catch (FtpChannel.FtpException f) {
				throw new TransportException(
						MessageFormat.format(JGitText.get().cannotListPackPath
								objectsPath
						f);
			}
			return packs;
		}

		@Override
		FileStream open(String path) throws IOException {
			try {
				return new FileStream(ftp.get(path));
			} catch (FtpChannel.FtpException f) {
				if (f.getStatus() == FtpChannel.FtpException.NO_SUCH_FILE) {
					throw new FileNotFoundException(path);
				}
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotGetObjectsPath
						f.getMessage())
			}
		}

		@Override
		void deleteFile(String path) throws IOException {
			try {
				ftp.delete(path);
			} catch (FtpChannel.FtpException f) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotDeleteObjectsPath
						path
			}

			String dir = path;
			int s = dir.lastIndexOf('/');
			while (s > 0) {
				try {
					dir = dir.substring(0
					ftp.rmdir(dir);
					s = dir.lastIndexOf('/');
				} catch (IOException je) {
					break;
				}
			}
		}

		@Override
		OutputStream writeFile(String path
				String monitorTask) throws IOException {
			Throwable err = null;
			try {
				return ftp.put(path);
			} catch (FileNotFoundException e) {
				mkdir_p(path);
			} catch (FtpChannel.FtpException je) {
				if (je.getStatus() == FtpChannel.FtpException.NO_SUCH_FILE) {
					mkdir_p(path);
				} else {
					err = je;
				}
			}
			if (err == null) {
				try {
					return ftp.put(path);
				} catch (IOException e) {
					err = e;
				}
			}
			throw new TransportException(
					MessageFormat.format(JGitText.get().cannotWriteObjectsPath
							objectsPath
					err);
		}

		@Override
		void writeFile(String path
			final String lock = path + LOCK_SUFFIX;
			try {
				super.writeFile(lock
				try {
					ftp.rename(lock
				} catch (IOException e) {
					throw new TransportException(MessageFormat.format(
							JGitText.get().cannotWriteObjectsPath
							path
				}
			} catch (IOException err) {
				try {
					ftp.rm(lock);
				} catch (IOException e) {
				}
				throw err;
			}
		}

		private void mkdir_p(String path) throws IOException {
			final int s = path.lastIndexOf('/');
			if (s <= 0)
				return;

			path = path.substring(0
			Throwable err = null;
			try {
				ftp.mkdir(path);
				return;
			} catch (FileNotFoundException f) {
				mkdir_p(path);
			} catch (FtpChannel.FtpException je) {
				if (je.getStatus() == FtpChannel.FtpException.NO_SUCH_FILE) {
					mkdir_p(path);
				} else {
					err = je;
				}
			}
			if (err == null) {
				try {
					ftp.mkdir(path);
					return;
				} catch (IOException e) {
					err = e;
				}
			}
			throw new TransportException(MessageFormat.format(
						JGitText.get().cannotMkdirObjectPath
					err.getMessage())
		}

		Map<String
			final TreeMap<String
			readPackedRefs(avail);
			readRef(avail
			readLooseRefs(avail
			return avail;
		}

		private void readLooseRefs(TreeMap<String
				String prefix) throws TransportException {
			final Collection<FtpChannel.DirEntry> list;
			try {
				list = ftp.ls(dir);
			} catch (IOException e) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotListObjectsPath
						e.getMessage())
			}

			for (FtpChannel.DirEntry ent : list) {
				String n = ent.getFilename();
					continue;

				if (ent.isDirectory()) {
					readLooseRefs(avail
				} else {
					readRef(avail
				}
			}
		}

		private Ref readRef(TreeMap<String
				String name) throws TransportException {
			final String line;
			try (BufferedReader br = openReader(path)) {
				line = br.readLine();
			} catch (FileNotFoundException noRef) {
				return null;
			} catch (IOException err) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().cannotReadObjectsPath
						err.getMessage())
			}

			if (line == null) {
				throw new TransportException(
						MessageFormat.format(JGitText.get().emptyRef
			}
				Ref r = avail.get(target);
				if (r == null)
					r = readRef(avail
				if (r == null)
					r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
				r = new SymbolicRef(name
				avail.put(r.getName()
				return r;
			}

			if (ObjectId.isId(line)) {
				final Ref r = new ObjectIdRef.Unpeeled(loose(avail.get(name))
						name
				avail.put(r.getName()
				return r;
			}

			throw new TransportException(
					MessageFormat.format(JGitText.get().badRef
		}

		private Storage loose(Ref r) {
			if (r != null && r.getStorage() == Storage.PACKED) {
				return Storage.LOOSE_PACKED;
			}
			return Storage.LOOSE;
		}

		@Override
		void close() {
			if (ftp != null) {
				try {
					if (ftp.isConnected()) {
						ftp.disconnect();
					}
				} finally {
					ftp = null;
				}
			}
		}
	}
}
