
package org.eclipse.jgit.internal.transport.connectivity;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ConnectivityChecker;
import org.eclipse.jgit.transport.ReceiveCommand;

public class IterativeConnectivityChecker implements ConnectivityChecker {
	private static final int MAXIMUM_PARENTS_TO_CHECK = 128;

	private final ConnectivityChecker delegate;

	private Set<ObjectId> forcedHaves = Collections.emptySet();

	public IterativeConnectivityChecker(ConnectivityChecker delegate) {
		this.delegate = delegate;
	}

	@Override
	public void checkConnectivity(ConnectivityCheckInfo connectivityCheckInfo
			Set<ObjectId> advertisedHaves
			throws MissingObjectException
		try {
			Set<ObjectId> newRefs = new HashSet<>();
			Set<ObjectId> expectedParents = new HashSet<>();

			getAllObjectIds(connectivityCheckInfo.getCommands())
					.forEach(oid -> {
						if (advertisedHaves.contains(oid)) {
							expectedParents.add(oid);
						} else {
							newRefs.add(oid);
						}
					});
			if (!newRefs.isEmpty()) {
				expectedParents.addAll(extractAdvertisedParentCommits(newRefs
						advertisedHaves
			}

			expectedParents.addAll(forcedHaves);

			if (!expectedParents.isEmpty()) {
				delegate.checkConnectivity(connectivityCheckInfo
						expectedParents
				return;
			}
		} catch (MissingObjectException e) {
		}
		delegate.checkConnectivity(connectivityCheckInfo
	}

	private static Stream<ObjectId> getAllObjectIds(
			List<ReceiveCommand> commands) {
		return commands.stream().flatMap(cmd -> {
			if (cmd.getType() == ReceiveCommand.Type.UPDATE || cmd
					.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
				return Stream.of(cmd.getOldId()
			} else if (cmd.getType() == ReceiveCommand.Type.CREATE) {
				return Stream.of(cmd.getNewId());
			}
			return Stream.of();
		});
	}

	public void setForcedHaves(Set<ObjectId> forcedHaves) {
		this.forcedHaves = Collections.unmodifiableSet(forcedHaves);
	}

	private static Set<ObjectId> extractAdvertisedParentCommits(
			Set<ObjectId> newRefs
			throws MissingObjectException
		Set<ObjectId> advertisedParents = new HashSet<>();
		for (ObjectId newRef : newRefs) {
			RevObject object = rw.parseAny(newRef);
			if (object instanceof RevCommit) {
				int numberOfParentsToCheck = 0;
				Queue<RevCommit> parents = new ArrayDeque<>(
						MAXIMUM_PARENTS_TO_CHECK);
				parents.addAll(
						parseParents(((RevCommit) object).getParents()
				while (!parents.isEmpty()) {
					RevCommit parentCommit = parents.poll();
					if (advertisedHaves.contains(parentCommit.getId())) {
						advertisedParents.add(parentCommit.getId());
					} else if (numberOfParentsToCheck < MAXIMUM_PARENTS_TO_CHECK) {
						RevCommit[] grandParents = parentCommit.getParents();
						numberOfParentsToCheck += grandParents.length;
						parents.addAll(parseParents(grandParents
					}
				}
			}
		}
		return advertisedParents;
	}

	private static List<RevCommit> parseParents(RevCommit[] parents
			RevWalk rw) {
		return Arrays.stream(parents).map((commit) -> {
			try {
				return rw.parseCommit(commit);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(toList());
	}
}
