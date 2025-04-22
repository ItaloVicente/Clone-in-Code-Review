
package org.eclipse.jgit.pgm;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Properties;

import org.eclipse.jgit.transport.AmazonS3;
import org.kohsuke.args4j.Argument;

@Command(name = "amazon-s3-client"
class AmazonS3Client extends TextBuiltin {
	@Argument(index = 0
	private File propertyFile;

	@Argument(index = 1
	private String op;

	@Argument(index = 2
	private String bucket;

	@Argument(index = 3
	private String key;

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		final AmazonS3 s3 = new AmazonS3(properties());

		if ("get".equals(op)) {
			final URLConnection c = s3.get(bucket
			int len = c.getContentLength();
			final InputStream in = c.getInputStream();
			try {
				final byte[] tmp = new byte[2048];
				while (len > 0) {
					final int n = in.read(tmp);
					if (n < 0)
						throw new EOFException("Expected " + len + " bytes.");
					System.out.write(tmp
					len -= n;
				}
			} finally {
				in.close();
			}

		} else if ("ls".equals(op) || "list".equals(op)) {
			for (final String k : s3.list(bucket
				System.out.println(k);

		} else if ("rm".equals(op) || "delete".equals(op)) {
			s3.delete(bucket

		} else if ("put".equals(op)) {
			final OutputStream os = s3.beginPut(bucket
			final byte[] tmp = new byte[2048];
			int n;
			while ((n = System.in.read(tmp)) > 0)
				os.write(tmp
			os.close();

		} else {
			throw die("Unsupported operation: " + op);
		}
	}

	private Properties properties() {
		try {
			final InputStream in = new FileInputStream(propertyFile);
			try {
				final Properties p = new Properties();
				p.load(in);
				return p;
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			throw die("no such file: " + propertyFile
		} catch (IOException e) {
			throw die("cannot read " + propertyFile
		}
	}
}
