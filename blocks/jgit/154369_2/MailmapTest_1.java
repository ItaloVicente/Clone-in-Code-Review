package org.eclipse.jgit.mailmap;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;

public class MailmapTest {

	@Test
	public void mapNullReturnsNull() {
		Mailmap mailmap = new Mailmap();
		assertNull(mailmap.map(null));
	}

	@Test
	public void noMatchLeavesIdentUnchanged() {
		Mailmap mailmap = new Mailmap();
		PersonIdent person = new PersonIdent("Proper Name"
		PersonIdent mapped = mailmap.map(person);
		assertThat(mapped
	}

	@Test
	public void previousEntriesTakePrecedence() {
		Mailmap mailmap = new Mailmap(Collections
				.singletonList(new MailmapEntry(null
		Mailmap appendMailmap = new Mailmap(Collections
				.singletonList(new MailmapEntry(null
		mailmap.append(appendMailmap);

		PersonIdent person = new PersonIdent("Old Name"
		PersonIdent mapped = mailmap.map(person);
		assertThat(mapped.getName()
	}
}
