
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchConstants.ACCEPTED;
import static org.eclipse.jgit.internal.ketch.KetchConstants.COMMITTED;
import static org.eclipse.jgit.internal.ketch.KetchConstants.CONFIG_KEY_TYPE;
import static org.eclipse.jgit.internal.ketch.KetchConstants.CONFIG_SECTION_KETCH;
import static org.eclipse.jgit.internal.ketch.KetchConstants.DEFAULT_TXN_NAMESPACE;
import static org.eclipse.jgit.internal.ketch.KetchConstants.STAGE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_NAME;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_REMOTE;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KetchSystem {
	private static final Random RNG = new Random();

	public static ScheduledExecutorService defaultExecutor() {
		return DefaultExecutorHolder.I;
	}

	private final ScheduledExecutorService executor;
	private final String txnNamespace;
	private final String txnAccepted;
	private final String txnCommitted;
	private final String txnStage;

	public KetchSystem() {
		this(defaultExecutor()
	}

	public KetchSystem(ScheduledExecutorService executor
		this.executor = executor;
		this.txnNamespace = txnNamespace;
		this.txnAccepted = getTxnNamespace() + ACCEPTED;
		this.txnCommitted = getTxnNamespace() + COMMITTED;
		this.txnStage = getTxnNamespace() + STAGE;
	}

	public ScheduledExecutorService getExecutor() {
		return executor;
	}

	public String getTxnNamespace() {
		return txnNamespace;
	}

	public String getTxnAccepted() {
		return txnAccepted;
	}

	public String getTxnCommitted() {
		return txnCommitted;
	}

	public String getTxnStage() {
		return txnStage;
	}

	public PersonIdent newCommitter() {
		return new PersonIdent(name
	}

	@Nullable
	public String newLeaderTag() {
		int n = RNG.nextInt(1 << (6 * 4));
		return String.format("%06x"
	}

	public KetchLeader createLeader(final Repository repo)
			throws URISyntaxException {
		KetchLeader leader = new KetchLeader(this) {
			@Override
			protected Repository openRepository() {
				repo.incrementOpen();
				return repo;
			}
		};
		leader.setReplicas(createReplicas(leader
		return leader;
	}

	protected List<KetchReplica> createReplicas(KetchLeader leader
			Repository repo) throws URISyntaxException {
		List<KetchReplica> replicas = new ArrayList<>();
		Config cfg = repo.getConfig();
		String localName = getLocalName(cfg);
		for (String name : cfg.getSubsections(CONFIG_KEY_REMOTE)) {
			if (!hasParticipation(cfg
				continue;
			}

			ReplicaConfig kc = ReplicaConfig.newFromConfig(cfg
			if (name.equals(localName)) {
				replicas.add(new LocalReplica(leader
				continue;
			}

			RemoteConfig rc = new RemoteConfig(cfg
			List<URIish> uris = rc.getPushURIs();
			if (uris.isEmpty()) {
				uris = rc.getURIs();
			}
			for (URIish uri : uris) {
				String n = uris.size() == 1 ? name : uri.getHost();
				replicas.add(new RemoteGitReplica(leader
			}
		}
		return replicas;
	}

	private static boolean hasParticipation(Config cfg
		return cfg.getString(CONFIG_KEY_REMOTE
	}

	private static String getLocalName(Config cfg) {
		return cfg.getString(CONFIG_SECTION_KETCH
	}

	static class DefaultExecutorHolder {
		private static final Logger log = LoggerFactory.getLogger(KetchSystem.class);
		static final ScheduledExecutorService I = create();

		private static ScheduledExecutorService create() {
			int cores = Runtime.getRuntime().availableProcessors();
			int threads = Math.max(5
			log.info("Using {} threads"
			return Executors.newScheduledThreadPool(
				threads
				new ThreadFactory() {
					private final AtomicInteger threadCnt = new AtomicInteger();

					@Override
					public Thread newThread(Runnable r) {
						int id = threadCnt.incrementAndGet();
						Thread thr = new Thread(r);
						return thr;
					}
				});
		}

		private DefaultExecutorHolder() {
		}
	}

	static long delay(long last
		long r = Math.max(0
		if (r > 0) {
			int c = (int) Math.min(r + 1
			r = RNG.nextInt(c);
		}
		return Math.max(Math.min(min + r
	}
}
