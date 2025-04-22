
package org.eclipse.jgit.transport;

import java.io.IOException;
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

public class SubscribedRepository {
	private final Repository repository;

	private final String remote;

	private final String name;

	private List<RefSpec> specs;

	public static String getPubSubRefFromRemote(String remote
		return translateRef(Constants.R_REMOTES + remote + "/"
				Constants.R_PUBSUB + remote + "/heads/"
	}

	public static String getPubSubRefFromLocal(String remote
		return translateRef(
				Constants.R_REFS
	}

	public static String getRemoteRefFromPubSub(String remote
		return translateRef(Constants.R_PUBSUB + remote + "/heads/"
				Constants.R_REMOTES + remote + "/"
	}

	public static String getLocalRefFromRemote(String remote
		return translateRef(Constants.R_REMOTES + remote + "/"
				Constants.R_REFS + "heads/"
	}

	public static String getRemoteRefFromLocal(String remote
		return translateRef(
				Constants.R_REFS + "heads/"
				ref);
	}

	public static String getLocalRefFromPubSub(String remote
		return translateRef(
				Constants.R_PUBSUB + remote + "/"
	}

	private static String translateRef(String oldPrefix
			String ref) {
		if (!ref.startsWith(oldPrefix))
			throw new IllegalArgumentException(ref + " does not start with "
					+ oldPrefix);
		return newPrefix + ref.substring(oldPrefix.length());
	}

	public SubscribedRepository(PubSubConfig.Subscriber s) throws IOException {
		this(s
	}

	public SubscribedRepository(PubSubConfig.Subscriber s
		remote = s.getRemote();
		repository = r;
		specs = s.getSubscribeSpecs();
		name = s.getName();
	}

	public void setUpRefs() throws IOException {
		Set<String> existingRefs = new LinkedHashSet<String>(
				repository.getRefDatabase().getRefs(
						getPubSubRefFromLocal(remote
						.keySet());
		Map<String
		for (Map.Entry<String
			String ref = entry.getKey();
			String existingRef = ref.substring(Constants.R_REFS.length());
			existingRefs.remove(existingRef);
			String pubsubRef = getPubSubRefFromLocal(remote
			if (repository.getRef(pubsubRef) != null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setExpectedOldObjectId(ObjectId.zeroId());
			ru.setNewObjectId(entry.getValue().getObjectId());
			ru.setRefLogMessage("pubsub setup"
			ru.update();
		}

		for (String r : existingRefs) {
			String pubsubRef = getPubSubRefFromLocal(
					remote
			if (repository.getRef(pubsubRef) == null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setForceUpdate(true);
			ru.delete();
		}
	}

	public Repository getRepository() {
		return repository;
	}

	public String getRemote() {
		return remote;
	}

	public List<RefSpec> getSubscribeSpecs() {
		return Collections.unmodifiableList(specs);
	}

	public void setSubscribeSpecs(List<RefSpec> s) {
		specs = s;
	}

	public Map<String
		Map<String
		RefDatabase rdb = repository.getRefDatabase();
		for (RefSpec spec : getSubscribeSpecs()) {
			String remoteRef;
			boolean isTag = spec.getSource().startsWith(Constants.R_TAGS);
			if (isTag)
				remoteRef = spec.getSource();
			else
				remoteRef = getRemoteRefFromLocal(remote
			Collection<Ref> c;
			if (spec.isWildcard()) {
				remoteRef = remoteRef.substring(0
				c = rdb.getRefs(remoteRef).values();
			} else {
				Ref r = rdb.getRef(remoteRef);
				if (r == null)
					continue;
				c = Collections.singleton(r);
			}
			for (Ref r : c) {
				if (isTag)
					matches.put(r.getName()
				else
					matches.put(getLocalRefFromRemote(getRemote()
							r);
			}
		}
		return matches;
	}

	public Map<String
		Map<String
		RefDatabase rdb = repository.getRefDatabase();
		for (RefSpec spec : getSubscribeSpecs()) {
			String pubsubRef = getPubSubRefFromLocal(remote
			if (spec.isWildcard()) {
				pubsubRef = pubsubRef.substring(0
				for (Ref r : rdb.getRefs(pubsubRef).values())
					matches.put(getLocalRefFromPubSub(remote
			} else {
				Ref r = rdb.getRef(pubsubRef);
				if (r != null)
					matches.put(getLocalRefFromPubSub(remote
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
