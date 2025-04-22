package org.eclipse.jgit.util;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;

import junit.framework.TestCase;

public class ChangeIdUtilTest extends TestCase {

	private static final ObjectId changeId = ObjectId
			.fromString("0123456789012345678901234567890123456789");

	public void testClean() {
		assertEquals("hej"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
				.clean("#no\nhej\nsan\nSigned-off-by: me \n#men"));
	}

	public void testId() throws IOException {
		String msg = "A\nMessage\n";
		final String i = "A U Thor <author@example.com> 1142878501 -0500";
		final String j = "W Riter <writer@example.com> 1142878502 -0500";
		final PersonIdent p = new PersonIdent(i);
		final PersonIdent q = new PersonIdent(j);
		ObjectId treeId = ObjectId
				.fromString("f51de923607cd51cf872b928a6b523ba823f7f35");
		ObjectId parentId = ObjectId
				.fromString("91fea719aaf9447feb9580477eb3dd08b62b5eca");
		ObjectId id = ChangeIdUtil.computeChangeId(treeId
		assertEquals("73f3751208ac92cbb76f9a26ac4a0d9d472e381b"
				.toString(id));
	}

	public void test_has_changeid() {
		assertEquals(
				"has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I0123456789012345678901234567890123456789\nAnd then some\n"
				ChangeIdUtil
						.insertId(
								"has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I0123456789012345678901234567890123456789\nAnd then some\n"
								changeId));
	}

	public void test_oneliner() {
		assertEquals(
				"oneliner\n\nChange-Id: I0123456789012345678901234567890123456789\n"
				ChangeIdUtil.insertId("oneliner\n"
	}

	public void test_oneliner_followed_by_blank() {
		assertEquals(
				"oneliner followed by blank\n\nChange-Id: I0123456789012345678901234567890123456789\n"
				ChangeIdUtil.insertId("oneliner followed by blank\n"
	}

	public void test_a_two_lines() {
		assertEquals(
				"a two lines\nwith text withour break after subject line\n\nChange-Id: I0123456789012345678901234567890123456789\n"
				ChangeIdUtil
						.insertId(
								"a two lines\nwith text withour break after subject line\n"
								changeId));
	}

	public void test_regular_commit() {
		assertEquals(
				"regular commit\n\nwith header and body\n\nChange-Id: I0123456789012345678901234567890123456789\n"
				ChangeIdUtil.insertId(
						"regular commit\n\nwith header and body\n"
	}

	public void test_regular_commit_with_sob__but_no_body() {
		assertEquals(
				"regular commit with sob
				ChangeIdUtil
						.insertId(
								"regular commit with sob
								changeId));
	}

	public void test_a_commit_with_bug__sub_but_no_body() {
		assertEquals(
				"a commit with bug
				ChangeIdUtil
						.insertId(
								"a commit with bug
								changeId));
	}

	public void test_a_commit_with_subject__no_body_sob_and_bug() {
		assertEquals(
				"a commit with subject
				ChangeIdUtil
						.insertId(
								"a commit with subject
								changeId));
	}

	public void test_a_commit_with_subject_bug__non_footer_line_and_sob() {
		assertEquals(
				"a commit with subject bug
				ChangeIdUtil
						.insertId(
								"a commit with subject bug
								changeId));
	}

	public void test_a_commit_with_subject__non_footer_and_bug_and_sob() {
		assertEquals(
				"a commit with subject
				ChangeIdUtil
						.insertId(
								"a commit with subject
								changeId));
	}

	public void test_a_commit_with_subject_body__bug__brackers_and_sob() {
		assertEquals(
				"a commit with subject body
				ChangeIdUtil
						.insertId(
								"a commit with subject body
								changeId));
	}

	public void test_a_commit_with_subject_body__bug__line_with_a_space_and_sob() {
		assertEquals(
				"a commit with subject body
				ChangeIdUtil
						.insertId(
								"a commit with subject body
								changeId));
	}

	public void test_a_commit_with_subject_body__bug__empty_line_and_sob() {
		assertEquals(
				"a commit with subject body
				ChangeIdUtil
						.insertId(
								"a commit with subject body
								changeId));
	}
}
