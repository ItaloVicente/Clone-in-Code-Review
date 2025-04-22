
package org.eclipse.jgit.pgm;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.eclipse.jgit.internal.ketch.KetchLeader;
import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
import org.eclipse.jgit.internal.ketch.KetchPreReceive;
import org.eclipse.jgit.internal.ketch.KetchSystem;
import org.eclipse.jgit.internal.ketch.KetchText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.DaemonClient;
import org.eclipse.jgit.transport.DaemonService;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Daemon extends TextBuiltin {
	@Option(name = "--config-file"
	File configFile;

	@Option(name = "--port"
	int port = org.eclipse.jgit.transport.Daemon.DEFAULT_PORT;

	@Option(name = "--listen"
	String host;

	@Option(name = "--timeout"
	int timeout = -1;

	@Option(name = "--enable"
	List<String> enable = new ArrayList<>();

	@Option(name = "--disable"
	List<String> disable = new ArrayList<>();

	@Option(name = "--allow-override"
	List<String> canOverride = new ArrayList<>();

	@Option(name = "--forbid-override"
	List<String> forbidOverride = new ArrayList<>();

	@Option(name = "--export-all"
	boolean exportAll;

	@Option(name = "--ketch"
	KetchServerType ketchServerType;

	enum KetchServerType {
		LEADER;
	}

	@Argument(required = true
	List<File> directory = new ArrayList<>();

	@Override
	protected boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		PackConfig packConfig = new PackConfig();

		if (configFile != null) {
			if (!configFile.exists()) {
				throw die(MessageFormat.format(
						CLIText.get().configFileNotFound
						configFile.getAbsolutePath()));
			}

			FileBasedConfig cfg = new FileBasedConfig(configFile
			cfg.load();
			new WindowCacheConfig().fromConfig(cfg).install();
			packConfig.fromConfig(cfg);
		}

		int threads = packConfig.getThreads();
		if (threads <= 0)
			threads = Runtime.getRuntime().availableProcessors();
		if (1 < threads)
			packConfig.setExecutor(Executors.newFixedThreadPool(threads));

		final FileResolver<DaemonClient> resolver = new FileResolver<>();
		for (File f : directory) {
			outw.println(MessageFormat.format(CLIText.get().exporting
			resolver.exportDirectory(f);
		}
		resolver.setExportAll(exportAll);

		final org.eclipse.jgit.transport.Daemon d;
		d = new org.eclipse.jgit.transport.Daemon(
				host != null ? new InetSocketAddress(host
						: new InetSocketAddress(port));
		d.setPackConfig(packConfig);
		d.setRepositoryResolver(resolver);
		if (0 <= timeout)
			d.setTimeout(timeout);

		for (String n : enable)
			service(d
		for (String n : disable)
			service(d

		for (String n : canOverride)
			service(d
		for (String n : forbidOverride)
			service(d
		if (ketchServerType == KetchServerType.LEADER) {
			startKetchLeader(d);
		}
		d.start();
		outw.println(MessageFormat.format(CLIText.get().listeningOn
	}

	private static DaemonService service(
			final org.eclipse.jgit.transport.Daemon d
			final String n) {
		final DaemonService svc = d.getService(n);
		if (svc == null)
			throw die(MessageFormat.format(CLIText.get().serviceNotSupported
		return svc;
	}

	private void startKetchLeader(org.eclipse.jgit.transport.Daemon daemon) {
		KetchSystem system = new KetchSystem();
		final KetchLeaderCache leaders = new KetchLeaderCache(system);
		final ReceivePackFactory<DaemonClient> factory;

		factory = daemon.getReceivePackFactory();
		daemon.setReceivePackFactory(new ReceivePackFactory<DaemonClient>() {
			@Override
			public ReceivePack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack rp = factory.create(req
				KetchLeader leader;
				try {
					leader = leaders.get(repo);
				} catch (URISyntaxException err) {
					throw new ServiceNotEnabledException(
							KetchText.get().invalidFollowerUri
				}
				rp.setPreReceiveHook(new KetchPreReceive(leader));
				return rp;
			}
		});
	}
}
