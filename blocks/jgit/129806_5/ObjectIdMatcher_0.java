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

	private Set<String> expectedOids;

	private ObjectIdMatcher(Set<String> oids) {
		this.expectedOids = oids;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("Object ids:");
		desc.appendValueList("<"
	}

	@Override
	protected boolean matchesSafely(Collection<ObjectId> objIds) {
		Set<String> asStrings = objIds.stream().map(ObjectId::name)
				.collect(Collectors.toSet());
		return asStrings.containsAll(expectedOids)
				&& expectedOids.containsAll(asStrings);
	}

	@Factory
	static Matcher<Collection<ObjectId>> hasOnlyObjectIds(
			String... oids) {
		return new ObjectIdMatcher(Sets.of(oids));
	}
}
