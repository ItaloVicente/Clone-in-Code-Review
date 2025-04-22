package org.eclipse.jgit.niofs.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HiddenBranchRefFilterTest {

	private HiddenBranchRefFilter filter;

	@Mock
	private Ref ref;
	private Map<String

	@Before
	public void setUp() {

		refs = new HashMap<>();
		refs.put("master"
		refs.put("develop"
		refs.put("PR--from/develop-master"
		refs.put("PR-1--master"
		refs.put("PR-master"
		refs.put("PR-1-from/develop-master"

		filter = new HiddenBranchRefFilter();
	}

	@Test
	public void testHiddenBranchsFiltering() {
		final Map<String
		final Set<Map.Entry<String
		assertEquals(5
		assertFalse(set.stream().anyMatch(entry -> entry.getKey().equals("PR-1-from/develop-master")));
	}
}
