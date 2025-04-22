
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.util.RefTranslator;

public class SubscribedRepository {
	private final Repository repository;

	private final String remoteName;

	private RemoteConfig remoteConfig;

	private final String name;

	private List<RefSpec> specs;

	private Map<String

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
						RefTranslator.getPubSubRefFromRemote(
								remoteName
		Map<String
		for (Map.Entry<String
			String ref = entry.getKey();
			String existingRef = ref.substring(Constants.R_REFS.length());
			existingRefs.remove(existingRef);
		}
		for (String r : existingRefs) {
			String pubsubRef = RefTranslator.getPubSubRefFromRemote(
					remoteName
			if (repository.getRef(pubsubRef) == null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setForceUpdate(true);
			ru.delete();
		}
		for (Map.Entry<String
			String ref = entry.getKey();
			String pubsubRef = RefTranslator.getPubSubRefFromRemote(
					remoteName
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
				remoteRef = RefTranslator.getTrackingRefFromRemote(
						remoteConfig
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
					matches.put(RefTranslator.getRemoteRefFromTracking(
							remoteConfig
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
			String pubsubRef = RefTranslator.getPubSubRefFromRemote(remoteName
			if (spec.isWildcard()) {
				pubsubRef = pubsubRef.substring(0
				for (Ref r : rdb.getRefs(pubsubRef).values())
					matches.put(RefTranslator.getRemoteRefFromPubSub(
							remoteName
			} else {
				Ref r = rdb.getRef(pubsubRef);
				if (r != null)
					matches.put(RefTranslator.getRemoteRefFromPubSub(
							remoteName
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
