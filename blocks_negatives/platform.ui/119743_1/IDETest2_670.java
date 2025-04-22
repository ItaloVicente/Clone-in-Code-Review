/*******************************************************************************
 * Copyright (c) 2020 Thomas Wolf<thomas.wolf@paranor.ch> and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.ui.tests.filteredtree;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.ui.internal.misc.TextMatcher;
import org.junit.Test;

/**
 * Tests for {@link TextMatcher}.
 */
public class TextMatcherTest {

	@Test
	public void testEmpty() {
		assertTrue(new TextMatcher("", false, false).match(""));
		assertFalse(new TextMatcher("", false, false).match("foo"));
		assertFalse(new TextMatcher("", false, false).match("foo bar baz"));
		assertTrue(new TextMatcher("", false, true).match(""));
		assertFalse(new TextMatcher("", false, true).match("foo"));
		assertFalse(new TextMatcher("", false, true).match("foo bar baz"));
	}

	@Test
	public void testSuffixes() {
		assertFalse(new TextMatcher("fo*ar", false, false).match("foobar_123"));
		assertFalse(new TextMatcher("fo*ar", false, false).match("foobar_baz"));
	}

	@Test
	public void testChinese() {
		assertTrue(new TextMatcher("åæ¬¢", false, false).match("æ åæ¬¢ å è¹æã"));
	}

	@Test
	public void testSingleWords() {
		assertTrue(new TextMatcher("huhn", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("h?hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("h*hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("hÃ¼hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h?hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h*hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("hÃ¼hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));

		assertTrue(new TextMatcher("huhn", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h?hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h*hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("hÃ¼hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h?hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("h*hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("hÃ¼hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
	}

	@Test
	public void testMultipleWords() {
		assertTrue(new TextMatcher("huhn h?hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn h?hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("huhn h?hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("huhn h?hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn h*hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn h*hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertFalse(new TextMatcher("huhn h*hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("huhn h*hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn hÃ¼hner", false, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn hÃ¼hner", false, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn hÃ¼hner", false, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("huhn hÃ¼hner", false, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
	}

	@Test
	public void testCaseInsensitivity() {
		assertTrue(new TextMatcher("Huhn HÃHNER", true, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("Huhn HÃHNER", true, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("Huhn HÃHNER", true, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertTrue(new TextMatcher("Huhn HÃHNER", true, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("HÃ¼HnEr", true, false).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("HÃ¼HnEr", true, false).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
		assertTrue(new TextMatcher("HÃ¼HnEr", true, true).match("hahn henne hÃ¼hner kÃ¼ken huhn"));
		assertFalse(new TextMatcher("HÃ¼HnEr", true, true).match("hahn henne hÃ¼hnerhof kÃ¼ken huhn"));
	}
}
