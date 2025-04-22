package org.eclipse.egit.ui.internal.fetch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FetchGerritChangeTest {

	@Test
	public void testChangeStringNull() {
		assertNull(FetchGerritChangePage.determineChangeFromString(null));
	}

	@Test
	public void testChangeStringEmpty() {
		assertNull(FetchGerritChangePage.determineChangeFromString(""));
	}

	@Test
	public void testChangeStringNoDigits() {
		assertNull(FetchGerritChangePage
				.determineChangeFromString("Just some string"));
	}

	@Test
	public void testChangeStringZero() {
		assertNull(FetchGerritChangePage.determineChangeFromString("0"));
	}

	@Test
	public void testChangeStringNegative() {
		assertNull(FetchGerritChangePage.determineChangeFromString("-17"));
	}

	@Test
	public void testChangeStringOverflow() {
		assertNull(FetchGerritChangePage
				.determineChangeFromString("4444444444444444/4"));
		assertNull(FetchGerritChangePage
				.determineChangeFromString("4/4444444444444444/"));
	}

	@Test
	public void testChangeStringUriNonsense() {
		assertNull(FetchGerritChangePage
				.determineChangeFromString("http://www.example.org/foo/bar"));
		assertNull(FetchGerritChangePage.determineChangeFromString(
				"https://git example.org/r/#/c/65510/"));
	}

	@Test
	public void testChangeStringMultiline() {
		assertNull(FetchGerritChangePage
				.determineChangeFromString("/10/\n65510/6"));
	}

	@Test
	public void testChangeStringUri() {
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/6"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/6/"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/6/some.path/some/File.txt"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/4..5"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/4..5/"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org/r/#/c/65510/4..5/some.path/some/File.txt"));
		assertEquals("65510", FetchGerritChangePage.determineChangeFromString(
				"https://git.example.org:8080/r/#/c/65510"));
	}

	@Test
	public void testChangeStringSingleNumber() {
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("65510"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/65510"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("65510/"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/65510/"));
	}

	@Test
	public void testChangeStringTwoNumbers() {
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("65510/6"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/65510/6"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("65510/6/"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/65510/6/"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("10/65510"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("10/65510/"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/10/65510"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/10/65510/"));
		assertEquals("10",
				FetchGerritChangePage.determineChangeFromString("/10/10"));
	}

	@Test
	public void testChangeStringThreeNumbers() {
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("10/65510/6"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("/10/65510/6"));
		assertEquals("65510",
				FetchGerritChangePage.determineChangeFromString("10/65510/6/"));
		assertEquals("65510", FetchGerritChangePage
				.determineChangeFromString("/10/65510/6/"));
		assertEquals("10",
				FetchGerritChangePage.determineChangeFromString("/10/10/6"));
		assertEquals("10",
				FetchGerritChangePage.determineChangeFromString("/65510/10/6"));
	}

}
