
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.hooks.Hooks;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackConfig;

public abstract class Transport implements AutoCloseable {
	public enum Operation {
		FETCH
		PUSH;
	}

	private static final List<WeakReference<TransportProtocol>> protocols =
		new CopyOnWriteArrayList<>();

	static {
		register(TransportLocal.PROTO_LOCAL);
		register(TransportBundleFile.PROTO_BUNDLE);
		register(TransportAmazonS3.PROTO_S3);
		register(TransportGitAnon.PROTO_GIT);
		register(TransportSftp.PROTO_SFTP);
		register(TransportHttp.PROTO_FTP);
		register(TransportHttp.PROTO_HTTP);
		register(TransportGitSsh.PROTO_SSH);

		registerByService();
	}

	private static void registerByService() {
		ClassLoader ldr = Thread.currentThread().getContextClassLoader();
		if (ldr == null)
			ldr = Transport.class.getClassLoader();
		Enumeration<URL> catalogs = catalogs(ldr);
		while (catalogs.hasMoreElements())
			scan(ldr
	}

	private static Enumeration<URL> catalogs(ClassLoader ldr) {
		try {
			String name = prefix + Transport.class.getName();
			return ldr.getResources(name);
		} catch (IOException err) {
			return new Vector<URL>().elements();
		}
	}

	private static void scan(ClassLoader ldr
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(url.openStream()
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0)
					continue;
				int comment = line.indexOf('#');
				if (comment == 0)
					continue;
				if (comment != -1)
					line = line.substring(0
				load(ldr
			}
		} catch (IOException e) {
		}
	}

	private static void load(ClassLoader ldr
		Class<?> clazz;
		try {
			clazz = Class.forName(cn
		} catch (ClassNotFoundException notBuiltin) {
			return;
		}

		for (Field f : clazz.getDeclaredFields()) {
			if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC
					&& TransportProtocol.class.isAssignableFrom(f.getType())) {
				TransportProtocol proto;
				try {
					proto = (TransportProtocol) f.get(null);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					continue;
				}
				if (proto != null)
					register(proto);
			}
		}
	}

	public static void register(TransportProtocol proto) {
		protocols.add(0
	}

	public static void unregister(TransportProtocol proto) {
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol refProto = ref.get();
			if (refProto == null || refProto == proto)
				protocols.remove(ref);
		}
	}

	public static List<TransportProtocol> getTransportProtocols() {
		int cnt = protocols.size();
		List<TransportProtocol> res = new ArrayList<>(cnt);
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto != null)
				res.add(proto);
			else
				protocols.remove(ref);
		}
		return Collections.unmodifiableList(res);
	}

	public static Transport open(Repository local
			throws NotSupportedException
			TransportException {
		return open(local
	}

	public static Transport open(final Repository local
			final Operation op) throws NotSupportedException
			URISyntaxException
		if (local != null) {
			final RemoteConfig cfg = new RemoteConfig(local.getConfig()
			if (doesNotExist(cfg))
				return open(local
			return open(local
		} else
			return open(new URIish(remote));

	}

	public static List<Transport> openAll(final Repository local
			final String remote) throws NotSupportedException
			URISyntaxException
		return openAll(local
	}

	public static List<Transport> openAll(final Repository local
			final String remote
			throws NotSupportedException
			TransportException {
		final RemoteConfig cfg = new RemoteConfig(local.getConfig()
		if (doesNotExist(cfg)) {
			final ArrayList<Transport> transports = new ArrayList<>(1);
			transports.add(open(local
			return transports;
		}
		return openAll(local
	}

	public static Transport open(Repository local
			throws NotSupportedException
		return open(local
	}

	public static Transport open(final Repository local
			final RemoteConfig cfg
			throws NotSupportedException
		final List<URIish> uris = getURIs(cfg
		if (uris.isEmpty())
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().remoteConfigHasNoURIAssociated
		final Transport tn = open(local
		tn.applyConfig(cfg);
		return tn;
	}

	public static List<Transport> openAll(final Repository local
			final RemoteConfig cfg) throws NotSupportedException
			TransportException {
		return openAll(local
	}

	public static List<Transport> openAll(final Repository local
			final RemoteConfig cfg
			throws NotSupportedException
		final List<URIish> uris = getURIs(cfg
		final List<Transport> transports = new ArrayList<>(uris.size());
		for (URIish uri : uris) {
			final Transport tn = open(local
			tn.applyConfig(cfg);
			transports.add(tn);
		}
		return transports;
	}

	private static List<URIish> getURIs(final RemoteConfig cfg
			final Operation op) {
		switch (op) {
		case FETCH:
			return cfg.getURIs();
		case PUSH: {
			List<URIish> uris = cfg.getPushURIs();
			if (uris.isEmpty())
				uris = cfg.getURIs();
			return uris;
		}
		default:
			throw new IllegalArgumentException(op.toString());
		}
	}

	private static boolean doesNotExist(RemoteConfig cfg) {
		return cfg.getURIs().isEmpty() && cfg.getPushURIs().isEmpty();
	}

	public static Transport open(Repository local
			throws NotSupportedException
		return open(local
	}

	public static Transport open(Repository local
			throws NotSupportedException
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto == null) {
				protocols.remove(ref);
				continue;
			}

			if (proto.canHandle(uri
				Transport tn = proto.open(uri
				tn.prePush = Hooks.prePush(local
				tn.prePush.setRemoteLocation(uri.toString());
				tn.prePush.setRemoteName(remoteName);
				return tn;
			}
		}

		throw new NotSupportedException(MessageFormat.format(JGitText.get().URINotSupported
	}

	public static Transport open(URIish uri) throws NotSupportedException
		for (WeakReference<TransportProtocol> ref : protocols) {
			TransportProtocol proto = ref.get();
			if (proto == null) {
				protocols.remove(ref);
				continue;
			}

			if (proto.canHandle(uri
				return proto.open(uri);
		}

		throw new NotSupportedException(MessageFormat.format(JGitText.get().URINotSupported
	}

	public static Collection<RemoteRefUpdate> findRemoteRefUpdatesFor(
			final Repository db
			final Map<String
			Collection<RefSpec> fetchSpecs) throws IOException {
		if (fetchSpecs == null)
			fetchSpecs = Collections.emptyList();
		final List<RemoteRefUpdate> result = new LinkedList<>();
		final Collection<RefSpec> procRefs = expandPushWildcardsFor(db

		for (RefSpec spec : procRefs) {
			String srcSpec = spec.getSource();
			final Ref srcRef = db.findRef(srcSpec);
			if (srcRef != null)
				srcSpec = srcRef.getName();

			String destSpec = spec.getDestination();
			if (destSpec == null) {
				destSpec = srcSpec;
			}

			if (srcRef != null && !destSpec.startsWith(Constants.R_REFS)) {
				final String n = srcRef.getName();
				final int kindEnd = n.indexOf('/'
				destSpec = n.substring(0
			}

			final boolean forceUpdate = spec.isForceUpdate();
			final String localName = findTrackingRefName(destSpec
			final RefLeaseSpec leaseSpec = leases.get(destSpec);
			final ObjectId expected = leaseSpec == null ? null :
				db.resolve(leaseSpec.getExpected());
			final RemoteRefUpdate rru = new RemoteRefUpdate(db
					destSpec
			result.add(rru);
		}
		return result;
	}

	public static Collection<RemoteRefUpdate> findRemoteRefUpdatesFor(
			final Repository db
			Collection<RefSpec> fetchSpecs) throws IOException {
		return findRemoteRefUpdatesFor(db
					       fetchSpecs);
	}

	private static Collection<RefSpec> expandPushWildcardsFor(
			final Repository db
			throws IOException {
		final List<Ref> localRefs = db.getRefDatabase().getRefs();
		final Collection<RefSpec> procRefs = new LinkedHashSet<>();

		for (RefSpec spec : specs) {
			if (spec.isWildcard()) {
				for (Ref localRef : localRefs) {
					if (spec.matchSource(localRef))
						procRefs.add(spec.expandFromSource(localRef));
				}
			} else {
				procRefs.add(spec);
			}
		}
		return procRefs;
	}

	private static String findTrackingRefName(final String remoteName
			final Collection<RefSpec> fetchSpecs) {
		for (RefSpec fetchSpec : fetchSpecs) {
			if (fetchSpec.matchSource(remoteName)) {
				if (fetchSpec.isWildcard())
					return fetchSpec.expandFromSource(remoteName)
							.getDestination();
				else
					return fetchSpec.getDestination();
			}
		}
		return null;
	}

	public static final boolean DEFAULT_FETCH_THIN = true;

	public static final boolean DEFAULT_PUSH_THIN = false;

	public static final RefSpec REFSPEC_TAGS = new RefSpec(

	public static final RefSpec REFSPEC_PUSH_ALL = new RefSpec(

	protected final Repository local;

	protected final URIish uri;

	private String optionUploadPack = RemoteConfig.DEFAULT_UPLOAD_PACK;

	private List<RefSpec> fetch = Collections.emptyList();

	private TagOpt tagopt = TagOpt.NO_TAGS;

	private boolean fetchThin = DEFAULT_FETCH_THIN;

	private String optionReceivePack = RemoteConfig.DEFAULT_RECEIVE_PACK;

	private List<RefSpec> push = Collections.emptyList();

	private boolean pushThin = DEFAULT_PUSH_THIN;

	private boolean pushAtomic;

	private boolean dryRun;

	private ObjectChecker objectChecker;

	private boolean removeDeletedRefs;

	private FilterSpec filterSpec = FilterSpec.NO_FILTER;

	private int timeout;

	private PackConfig packConfig;

	private CredentialsProvider credentialsProvider;

	private List<String> pushOptions;

	private PrintStream hookOutRedirect;

	private PrePushHook prePush;
	protected Transport(Repository local
		final TransferConfig tc = local.getConfig().get(TransferConfig.KEY);
		this.local = local;
		this.uri = uri;
		this.objectChecker = tc.newObjectChecker();
		this.credentialsProvider = CredentialsProvider.getDefault();
		prePush = Hooks.prePush(local
	}

	protected Transport(URIish uri) {
		this.uri = uri;
		this.local = null;
		this.objectChecker = new ObjectChecker();
		this.credentialsProvider = CredentialsProvider.getDefault();
	}

	public URIish getURI() {
		return uri;
	}

	public String getOptionUploadPack() {
		return optionUploadPack;
	}

	public void setOptionUploadPack(String where) {
		if (where != null && where.length() > 0)
			optionUploadPack = where;
		else
			optionUploadPack = RemoteConfig.DEFAULT_UPLOAD_PACK;
	}

	public TagOpt getTagOpt() {
		return tagopt;
	}

	public void setTagOpt(TagOpt option) {
		tagopt = option != null ? option : TagOpt.AUTO_FOLLOW;
	}

	public boolean isFetchThin() {
		return fetchThin;
	}

	public void setFetchThin(boolean fetchThin) {
		this.fetchThin = fetchThin;
	}

	public boolean isCheckFetchedObjects() {
		return getObjectChecker() != null;
	}

	public void setCheckFetchedObjects(boolean check) {
		if (check && objectChecker == null)
			setObjectChecker(new ObjectChecker());
		else if (!check && objectChecker != null)
			setObjectChecker(null);
	}

	public ObjectChecker getObjectChecker() {
		return objectChecker;
	}

	public void setObjectChecker(ObjectChecker impl) {
		objectChecker = impl;
	}

	public String getOptionReceivePack() {
		return optionReceivePack;
	}

	public void setOptionReceivePack(String optionReceivePack) {
		if (optionReceivePack != null && optionReceivePack.length() > 0)
			this.optionReceivePack = optionReceivePack;
		else
			this.optionReceivePack = RemoteConfig.DEFAULT_RECEIVE_PACK;
	}

	public boolean isPushThin() {
		return pushThin;
	}

	public void setPushThin(boolean pushThin) {
		this.pushThin = pushThin;
	}

	public boolean isPushAtomic() {
		return pushAtomic;
	}

	public void setPushAtomic(boolean atomic) {
		this.pushAtomic = atomic;
	}

	public boolean isRemoveDeletedRefs() {
		return removeDeletedRefs;
	}

	public void setRemoveDeletedRefs(boolean remove) {
		removeDeletedRefs = remove;
	}

	@Deprecated
	public final long getFilterBlobLimit() {
		return filterSpec.getBlobLimit();
	}

	@Deprecated
	public final void setFilterBlobLimit(long bytes) {
		setFilterSpec(FilterSpec.withBlobLimit(bytes));
	}

	public final FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public final void setFilterSpec(@NonNull FilterSpec filter) {
		filterSpec = requireNonNull(filter);
	}

	public void applyConfig(RemoteConfig cfg) {
		setOptionUploadPack(cfg.getUploadPack());
		setOptionReceivePack(cfg.getReceivePack());
		setTagOpt(cfg.getTagOpt());
		fetch = cfg.getFetchRefSpecs();
		push = cfg.getPushRefSpecs();
		timeout = cfg.getTimeout();
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int seconds) {
		timeout = seconds;
	}

	public PackConfig getPackConfig() {
		if (packConfig == null)
			packConfig = new PackConfig(local);
		return packConfig;
	}

	public void setPackConfig(PackConfig pc) {
		packConfig = pc;
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	public List<String> getPushOptions() {
		return pushOptions;
	}

	public void setPushOptions(List<String> pushOptions) {
		this.pushOptions = pushOptions;
	}

	public FetchResult fetch(final ProgressMonitor monitor
			Collection<RefSpec> toFetch) throws NotSupportedException
			TransportException {
		if (toFetch == null || toFetch.isEmpty()) {
			if (fetch.isEmpty())
				throw new TransportException(JGitText.get().nothingToFetch);
			toFetch = fetch;
		} else if (!fetch.isEmpty()) {
			final Collection<RefSpec> tmp = new ArrayList<>(toFetch);
			for (RefSpec requested : toFetch) {
				final String reqSrc = requested.getSource();
				for (RefSpec configured : fetch) {
					final String cfgSrc = configured.getSource();
					final String cfgDst = configured.getDestination();
					if (cfgSrc.equals(reqSrc) && cfgDst != null) {
						tmp.add(configured);
						break;
					}
				}
			}
			toFetch = tmp;
		}

		final FetchResult result = new FetchResult();
		new FetchProcess(this

		local.autoGC(monitor);

		return result;
	}

	public PushResult push(final ProgressMonitor monitor
			Collection<RemoteRefUpdate> toPush
			throws NotSupportedException
			TransportException {
		if (toPush == null || toPush.isEmpty()) {
			try {
				toPush = findRemoteRefUpdatesFor(push);
			} catch (final IOException e) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().problemWithResolvingPushRefSpecsLocally
			}
			if (toPush.isEmpty())
				throw new TransportException(JGitText.get().nothingToPush);
		}
		if (prePush != null) {
			try {
				prePush.setRefs(toPush);
				prePush.call();
			} catch (AbortedByHookException | IOException e) {
				throw new TransportException(e.getMessage()
			}
		}

		final PushProcess pushProcess = new PushProcess(this
		return pushProcess.execute(monitor);
	}

	public PushResult push(final ProgressMonitor monitor
			Collection<RemoteRefUpdate> toPush) throws NotSupportedException
			TransportException {
		return push(monitor
	}

	public Collection<RemoteRefUpdate> findRemoteRefUpdatesFor(
			final Collection<RefSpec> specs) throws IOException {
		return findRemoteRefUpdatesFor(local
					       fetch);
	}

	public Collection<RemoteRefUpdate> findRemoteRefUpdatesFor(
			final Collection<RefSpec> specs
			final Map<String
		return findRemoteRefUpdatesFor(local
					       fetch);
	}

	public abstract FetchConnection openFetch() throws NotSupportedException
			TransportException;

	public abstract PushConnection openPush() throws NotSupportedException
			TransportException;

	@Override
	public abstract void close();
}
