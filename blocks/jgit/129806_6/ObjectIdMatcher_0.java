package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Sets;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

class ObjectIdMatcher extends TypeSafeMatcher<Collection<ObjectId>> {

	private final Set<ObjectId> expectedOids;

	private ObjectIdMatcher(Set<String> oids) {
		this.expectedOids = oids.stream().map(ObjectId::fromString)
				.collect(Collectors.toSet());
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("Object ids:");
		desc.appendValueList("<"
	}

	@Override
	protected boolean matchesSafely(Collection<ObjectId> resultOids) {
		return resultOids.containsAll(expectedOids)
				&& expectedOids.containsAll(resultOids);
	}

	@Factory
	static Matcher<Collection<ObjectId>> hasOnlyObjectIds(
			String... oids) {
		return new ObjectIdMatcher(Sets.of(oids));
	}
}
