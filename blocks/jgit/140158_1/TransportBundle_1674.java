
package org.eclipse.jgit.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

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

public class TransportAmazonS3 extends HttpTransport implements WalkTransport {

	static final TransportProtocol PROTO_S3 = new TransportProtocol() {
		@Override
		public String getName() {
		}

		@Override
		public Set<String> getSchemes() {
			return Collections.singleton(S3_SCHEME);
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.HOST
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.PASS));
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportAmazonS3(local
		}
	};

	final AmazonS3 s3;

	final String bucket;

	private final String keyPrefix;

	TransportAmazonS3(final Repository local
			throws NotSupportedException {
		super(local

		Properties props = loadProperties();
		File directory = local.getDirectory();
			props.put("tmpdir"

		s3 = new AmazonS3(props);
		bucket = uri.getHost();

		String p = uri.getPath();
			p = p.substring(1);
			p = p.substring(0
		keyPrefix = p;
	}

	private Properties loadProperties() throws NotSupportedException {
		if (local.getDirectory() != null) {
			File propsFile = new File(local.getDirectory()
			if (propsFile.isFile())
				return loadPropertiesFile(propsFile);
		}

		File propsFile = new File(local.getFS().userHome()
		if (propsFile.isFile())
			return loadPropertiesFile(propsFile);

		Properties props = new Properties();
		String user = uri.getUser();
		String pass = uri.getPass();
		if (user != null && pass != null) {
		        props.setProperty("accesskey"
		        props.setProperty("secretkey"
		} else
			throw new NotSupportedException(MessageFormat.format(
					JGitText.get().cannotReadFile
		return props;
	}

	private static Properties loadPropertiesFile(File propsFile)
			throws NotSupportedException {
		try {
			return AmazonS3.properties(propsFile);
		} catch (IOException e) {
			throw new NotSupportedException(MessageFormat.format(
					JGitText.get().cannotReadFile
		}
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		final DatabaseS3 c = new DatabaseS3(bucket
		final WalkFetchConnection r = new WalkFetchConnection(this
		r.available(c.readAdvertisedRefs());
		return r;
	}

	@Override
	public PushConnection openPush() throws TransportException {
		final DatabaseS3 c = new DatabaseS3(bucket
		final WalkPushConnection r = new WalkPushConnection(this
		r.available(c.readAdvertisedRefs());
		return r;
	}

	@Override
	public void close() {
	}

	class DatabaseS3 extends WalkRemoteObjectDatabase {
		private final String bucketName;

		private final String objectsKey;

		DatabaseS3(final String b
			bucketName = b;
			objectsKey = o;
		}

		private String resolveKey(String subpath) {
				subpath = subpath.substring(0
			String k = objectsKey;
			while (subpath.startsWith(ROOT_DIR)) {
				k = k.substring(0
				subpath = subpath.substring(3);
			}
		}

		@Override
		URIish getURI() {
			URIish u = new URIish();
			u = u.setScheme(S3_SCHEME);
			u = u.setHost(bucketName);
			return u;
		}

		@Override
		Collection<WalkRemoteObjectDatabase> getAlternates() throws IOException {
			try {
				return readAlternates(INFO_ALTERNATES);
			} catch (FileNotFoundException err) {
			}
			return null;
		}

		@Override
		WalkRemoteObjectDatabase openAlternate(String location)
				throws IOException {
			return new DatabaseS3(bucketName
		}

		@Override
		Collection<String> getPackNames() throws IOException {
			final HashSet<String> have = new HashSet<>();
			have.addAll(s3.list(bucket

			final Collection<String> packs = new ArrayList<>();
			for (String n : have) {
					continue;

				final String in = n.substring(0
				if (have.contains(in))
					packs.add(n);
			}
			return packs;
		}

		@Override
		FileStream open(String path) throws IOException {
			final URLConnection c = s3.get(bucket
			final InputStream raw = c.getInputStream();
			final InputStream in = s3.decrypt(c);
			final int len = c.getContentLength();
			return new FileStream(in
		}

		@Override
		void deleteFile(String path) throws IOException {
			s3.delete(bucket
		}

		@Override
		OutputStream writeFile(final String path
				final ProgressMonitor monitor
				throws IOException {
			return s3.beginPut(bucket
		}

		@Override
		void writeFile(String path
			s3.put(bucket
		}

		Map<String
			final TreeMap<String
			readPackedRefs(avail);
			readLooseRefs(avail);
			readRef(avail
			return avail;
		}

		private void readLooseRefs(TreeMap<String
				throws TransportException {
			try {
				for (final String n : s3.list(bucket
					readRef(avail
			} catch (IOException e) {
				throw new TransportException(getURI()
			}
		}

		private Ref readRef(TreeMap<String
				throws TransportException {
			final String s;
			String ref = ROOT_DIR + rn;
			try {
				try (BufferedReader br = openReader(ref)) {
					s = br.readLine();
				}
			} catch (FileNotFoundException noRef) {
				return null;
			} catch (IOException err) {
				throw new TransportException(getURI()
						JGitText.get().transportExceptionReadRef
			}

			if (s == null)
				throw new TransportException(getURI()

				Ref r = avail.get(target);
				if (r == null)
					r = readRef(avail
				if (r == null)
					r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
				r = new SymbolicRef(rn
				avail.put(r.getName()
				return r;
			}

			if (ObjectId.isId(s)) {
				final Ref r = new ObjectIdRef.Unpeeled(loose(avail.get(rn))
						rn
				avail.put(r.getName()
				return r;
			}

			throw new TransportException(getURI()
		}

		private Storage loose(Ref r) {
			if (r != null && r.getStorage() == Storage.PACKED)
				return Storage.LOOSE_PACKED;
			return Storage.LOOSE;
		}

		@Override
		void close() {
		}
	}
}
