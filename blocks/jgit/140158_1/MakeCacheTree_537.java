
package org.eclipse.jgit.pgm.debug;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lfs.server.LargeFileRepository;
import org.eclipse.jgit.lfs.server.LfsProtocolServlet;
import org.eclipse.jgit.lfs.server.fs.FileLfsRepository;
import org.eclipse.jgit.lfs.server.fs.FileLfsServlet;
import org.eclipse.jgit.lfs.server.s3.S3Config;
import org.eclipse.jgit.lfs.server.s3.S3Repository;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class LfsStore extends TextBuiltin {

	static class AppServer {

		private final Server server;

		private final ServerConnector connector;

		private final ContextHandlerCollection contexts;

		private URI uri;

		AppServer(int port) {
			server = new Server();

			HttpConfiguration http_config = new HttpConfiguration();
			http_config.setOutputBufferSize(32768);

			connector = new ServerConnector(server
					new HttpConnectionFactory(http_config));
			connector.setPort(port);
			try {
						.getHostAddress();
				connector.setHost(host);
			} catch (UnknownHostException e) {
				throw new RuntimeException("Cannot find localhost"
			} catch (URISyntaxException e) {
				throw new RuntimeException("Unexpected URI error on " + uri
			}

			contexts = new ContextHandlerCollection();
			server.setHandler(contexts);
			server.setConnectors(new Connector[] { connector });
		}

		ServletContextHandler addContext(String path) {
			assertNotRunning();

			ServletContextHandler ctx = new ServletContextHandler();
			ctx.setContextPath(path);
			contexts.addHandler(ctx);

			return ctx;
		}

		void start() throws Exception {
			server.start();
		}

		void stop() throws Exception {
			server.stop();
		}

		URI getURI() {
			return uri;
		}

		private void assertNotRunning() {
			if (server.isRunning()) {
			}
		}
	}

	private static enum StoreType {
		FS
	}

	private static enum StorageClass {
		REDUCED_REDUNDANCY
	}




	@Option(name = "--port"
			metaVar = "metaVar_port"
	int port;

	@Option(name = "--store"
	StoreType storeType;

	@Option(name = "--store-url"
			usage = "usage_LFSStoreUrl")
	String storeUrl;

	@Option(name = "--region"
			metaVar = "metaVar_s3Region"

	@Option(name = "--bucket"
			metaVar = "metaVar_s3Bucket"

	@Option(name = "--storage-class"
			metaVar = "metaVar_s3StorageClass"
	StorageClass storageClass = StorageClass.REDUCED_REDUNDANCY;

	@Option(name = "--expire"
			metaVar = "metaVar_seconds"
	int expirationSeconds = 600;

	@Option(name = "--no-ssl-verify"
	boolean disableSslVerify = false;

	@Argument(required = false
	String directory;

	String protocolUrl;

	String accessKey;

	String secretKey;

	@Override
	protected boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		AppServer server = new AppServer(port);
		URI baseURI = server.getURI();

		final LargeFileRepository repository;
		switch (storeType) {
		case FS:
			Path dir = Paths.get(directory);
			FileLfsRepository fsRepo = new FileLfsRepository(
					getStoreUrl(baseURI)
			FileLfsServlet content = new FileLfsServlet(fsRepo
			app.addServlet(new ServletHolder(content)
			repository = fsRepo;
			break;

		case S3:
			readAWSKeys();
			checkOptions();
			S3Config config = new S3Config(region
					storageClass.toString()
					expirationSeconds
			repository = new S3Repository(config);
			break;
		default:
			throw new IllegalArgumentException(MessageFormat
					.format(CLIText.get().lfsUnknownStoreType
		}

		LfsProtocolServlet protocol = new LfsProtocolServlet() {

			private static final long serialVersionUID = 1L;

			@Override
			protected LargeFileRepository getLargeFileRepository(
					LfsRequest request
				return repository;
			}
		};
		app.addServlet(new ServletHolder(protocol)

		server.start();

		outw.println(MessageFormat.format(CLIText.get().lfsProtocolUrl
				getProtocolUrl(baseURI)));
		if (storeType == StoreType.FS) {
			outw.println(MessageFormat.format(CLIText.get().lfsStoreDirectory
					directory));
			outw.println(MessageFormat.format(CLIText.get().lfsStoreUrl
					getStoreUrl(baseURI)));
		}
	}

	private void checkOptions() {
		if (bucket == null || bucket.length() == 0) {
			throw die(MessageFormat.format(CLIText.get().s3InvalidBucket
					bucket));
		}
	}

	private void readAWSKeys() throws IOException
		FileBasedConfig c = new FileBasedConfig(new File(credentialsPath)
				FS.DETECTED);
		c.load();
		accessKey = c.getString("default"
		secretKey = c.getString("default"
		if (accessKey == null || accessKey.isEmpty()) {
			throw die(MessageFormat.format(CLIText.get().lfsNoAccessKey
					credentialsPath));
		}
		if (secretKey == null || secretKey.isEmpty()) {
			throw die(MessageFormat.format(CLIText.get().lfsNoSecretKey
					credentialsPath));
		}
	}

	private String getStoreUrl(URI baseURI) {
		if (storeUrl == null) {
			if (storeType == StoreType.FS) {
			} else {
			}
		}
		return storeUrl;
	}

	private String getProtocolUrl(URI baseURI) {
		if (protocolUrl == null) {
			protocolUrl = baseURI + PROTOCOL_PATH;
		}
		return protocolUrl;
	}
}
