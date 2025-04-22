
package org.eclipse.jgit.pgm;

import static java.lang.Integer.valueOf;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Properties;

import org.eclipse.jgit.pgm.internal.CLIText;
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

		if (null == op) {
                    throw die(MessageFormat.format(CLIText.get().unsupportedOperation
                } else switch (op) {
                case "get":
                    final URLConnection c = s3.get(bucket
                    int len = c.getContentLength();
                    try (InputStream in = c.getInputStream()) {
                        outw.flush();
                        final byte[] tmp = new byte[2048];
                        while (len > 0) {
                            final int n = in.read(tmp);
                            if (n < 0)
                                throw new EOFException(MessageFormat.format(
                                        CLIText.get().expectedNumberOfbytes
                                        valueOf(len)));
                            outs.write(tmp
                            len -= n;
                        }
                        outs.flush();
                    }
                    break;
                case "ls":
                case "list":
                    for (String k : s3.list(bucket
                        outw.println(k);
                    break;
                case "rm":
                case "delete":
                    s3.delete(bucket
                    break;
                case "put":
                    try (OutputStream os = s3.beginPut(bucket
                        final byte[] tmp = new byte[2048];
                        int n;
                        while ((n = ins.read(tmp)) > 0)
                            os.write(tmp
                    }
                    break;
                default:
                    throw die(MessageFormat.format(CLIText.get().unsupportedOperation
            }
	}

	private Properties properties() {
		try {
			try (InputStream in = new FileInputStream(propertyFile)) {
				final Properties p = new Properties();
				p.load(in);
				return p;
			}
		} catch (FileNotFoundException e) {
			throw die(MessageFormat.format(CLIText.get().noSuchFile
		} catch (IOException e) {
			throw die(MessageFormat.format(CLIText.get().cannotReadBecause
		}
	}
}
