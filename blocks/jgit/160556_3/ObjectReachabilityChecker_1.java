package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface ObjectReachabilityChecker {

	Optional<RevObject> areAllReachable(Collection<RevObject> targets
			Stream<RevObject> starters) throws IOException;

}
