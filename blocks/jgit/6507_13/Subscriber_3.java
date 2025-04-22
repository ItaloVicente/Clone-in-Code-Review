
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class SubscribedRepository {
	private final Repository repository;

	private final String remoteName;

	private RemoteConfig remoteConfig;

	private final String name;

	private List<RefSpec> specs;

	private Map<String

	public static String getPubSubRefFromTracking(
			RemoteConfig rc
		String ref = getRemoteRefFromTracking(rc
		return getPubSubRefFromRemote(rc.getName()
	}

	public static String getPubSubRefFromRemote(
			String remote
		return translateRef(
				Constants.R_REFS
	}

	public static String getTrackingRefFromPubSub(
			RemoteConfig rc
		String remote = getRemoteRefFromPubSub(rc.getName()
		return getTrackingRefFromRemote(rc
	}

	public static String getRemoteRefFromTracking(
			RemoteConfig rc
		String local = null;
		for (RefSpec r : rc.getFetchRefSpecs()) {
			if (r.matchDestination(trackingRef)) {
				if (r.isWildcard())
					local = r.getSource()
							.substring(0
							+ trackingRef.substring(
									r.getDestination().length() - 1);
				else
					local = r.getSource();
			}
		}
		if (local == null)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().noMatchingFetchSpec
					rc.getName()));
		return local;
	}

	public static String getTrackingRefFromRemote(
			RemoteConfig rc
		String local = null;
		for (RefSpec r : rc.getFetchRefSpecs()) {
			if (r.matchSource(remoteRef)) {
				if (r.isWildcard())
					local = r.getDestination()
							.substring(0
							+ remoteRef.substring(r.getSource().length() - 1);
				else
					local = r.getDestination();
			}
		}
		if (local == null)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().noMatchingFetchSpec
					rc.getName()));
		return local;
	}

	public static String getRemoteRefFromPubSub(String remote
		return translateRef(
				Constants.R_PUBSUB + remote + "/"
	}

	private static String translateRef(String oldPrefix
			String ref) {
		if (!ref.startsWith(oldPrefix))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidRefName
		return newPrefix + ref.substring(oldPrefix.length());
	}

	public SubscribedRepository(PubSubConfig.Subscriber s)
			throws IOException
		this(s
	}

	public SubscribedRepository(PubSubConfig.Subscriber s
		remoteName = s.getRemote();
		repository = r;
		specs = s.getSubscribeSpecs();
		name = s.getName();
	}

	public void setUpRefs() throws IOException
		remoteConfig = new RemoteConfig(repository.getConfig()
		Set<String> existingRefs = new LinkedHashSet<String>(
				repository.getRefDatabase().getRefs(
						getPubSubRefFromRemote(remoteName
						.keySet());
		Map<String
		for (Map.Entry<String
			String ref = entry.getKey();
			String existingRef = ref.substring(Constants.R_REFS.length());
			existingRefs.remove(existingRef);
		}
		for (String r : existingRefs) {
			String pubsubRef = getPubSubRefFromRemote(
					remoteName
			if (repository.getRef(pubsubRef) == null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setForceUpdate(true);
			ru.delete();
		}
		for (Map.Entry<String
			String ref = entry.getKey();
			String pubsubRef = getPubSubRefFromRemote(remoteName
			if (repository.getRef(pubsubRef) != null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setExpectedOldObjectId(ObjectId.zeroId());
			ru.setNewObjectId(entry.getValue().getObjectId());
			ru.setRefLogMessage("pubsub setup"
			ru.forceUpdate();
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public String getRemote() {
		return remoteName;
	}

	public List<RefSpec> getSubscribeSpecs() {
		return Collections.unmodifiableList(specs);
	}

	public void setSubscribeSpecs(List<RefSpec> s) {
		specs = s;
		remoteRefs = null;
	}

	private void cacheRemoteRefs() throws IOException {
		Map<String
		RefDatabase refdb = repository.getRefDatabase();
		for (RefSpec spec : getSubscribeSpecs()) {
			String remoteRef;
			boolean isTag = spec.getSource().startsWith(Constants.R_TAGS);
			if (isTag)
				remoteRef = spec.getSource();
			else
				remoteRef = getTrackingRefFromRemote(remoteConfig
			Collection<Ref> c;
			if (spec.isWildcard()) {
				remoteRef = remoteRef.substring(0
				c = refdb.getRefs(remoteRef).values();
			} else {
				Ref r = refdb.getRef(remoteRef);
				if (r == null)
					continue;
				c = Collections.singleton(r);
			}
			for (Ref r : c) {
				if (isTag)
					matches.put(r.getName()
				else
					matches.put(getRemoteRefFromTracking(remoteConfig
			}
		}
		remoteRefs = matches;
	}

	public Map<String
		if (remoteRefs == null)
			cacheRemoteRefs();
		return remoteRefs;
	}

	public Map<String
		Map<String
		RefDatabase rdb = repository.getRefDatabase();
		for (RefSpec spec : getSubscribeSpecs()) {
			String pubsubRef = getPubSubRefFromRemote(remoteName
			if (spec.isWildcard()) {
				pubsubRef = pubsubRef.substring(0
				for (Ref r : rdb.getRefs(pubsubRef).values())
					matches.put(getRemoteRefFromPubSub(remoteName
			} else {
				Ref r = rdb.getRef(pubsubRef);
				if (r != null)
					matches.put(getRemoteRefFromPubSub(remoteName
			}
		}
		return matches;
	}

	public String getName() {
		return name;
	}

	public void close() {
		repository.close();
	}
}
