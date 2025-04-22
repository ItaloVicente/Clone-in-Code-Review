
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class SubscribedRepository {
	public static final String PUBSUB_REF_PREFIX = "refs/pubsub/";

	private final Repository repository;

	private final String remote;

	private final String name;

	private List<RefSpec> specs;

	public static String getSubscribedRef(String remote
		return PUBSUB_REF_PREFIX + remote + ref.substring(
				Constants.R_REFS.length() - 1);
	}

	public SubscribedRepository(PubSubConfig.Subscriber s) throws IOException {
		remote = s.getRemote();
		repository = new FileRepository(s.getDirectory());
		specs = s.getSubscribeSpecs();
		name = s.getName();

		Map<String
		for (Map.Entry<String
			String pubsubRef = getSubscribedRef(s.getRemote()
			if (repository.getRef(pubsubRef) != null)
				continue;
			RefUpdate ru = repository.updateRef(pubsubRef);
			ru.setExpectedOldObjectId(ObjectId.zeroId());
			ru.setNewObjectId(entry.getValue().getObjectId());
			ru.update();
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
		return getRefFilter().filter(repository.getAllRefs());
	}

	public RefFilter getRefFilter() {
		return new RefFilter() {
			public Map<String
				Map<String
				for (Map.Entry<String
					for (RefSpec spec : getSubscribeSpecs()) {
						if (spec.matchSource(e.getValue()))
							ret.put(e.getKey()
					}
				}
				return ret;
			}
		};
	}

	public Map<String
		Map<String
		RefDatabase rdb = repository.getRefDatabase();
		for (RefSpec spec : getSubscribeSpecs()) {
			String pubsubRef = getSubscribedRef(remote
			if (spec.isWildcard()) {
				pubsubRef = pubsubRef.substring(0
				for (Ref r : rdb.getRefs(pubsubRef).values()) {
					matches.put(r.getName()
				}
			} else {
				Ref r = rdb.getRef(pubsubRef);
				matches.put(r.getName()
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
