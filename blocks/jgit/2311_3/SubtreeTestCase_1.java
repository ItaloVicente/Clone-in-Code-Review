
package org.eclipse.jgit.subtree;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.junit.Test;

public class SubtreeSplitterTest extends SubtreeTestCase {

	@Test
	public void testAddSubtreeOnBranch() throws Exception {
		RevCommit A = commit(tree(file("a"
		RevCommit B = commit(tree(file("a"
		RevCommit D = commit(tree(file("b"
		RevCommit C = subtreeAdd("suba"
		rw.parseCommit(C);
		RevCommit E = commit(editTree(C.getTree()

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.normal("B"
		sv.normal("C"
		sv.subtree("D"
		sv.normal("E"
		sv.setStart(E);
		sv.validate();
	}

	@Test
	public void testDeleteSubtree() throws Exception {
		RevTree parentTree = rw.parseCommit(commit(tree(file("a"
				.getTree();

		RevCommit D = commit(tree(file("b"
		RevCommit A = commit(subtreeAdd("suba"
				.getTree()
		RevCommit B = commit(parentTree
		RevCommit C = commit(tree(file("a"

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.normal("B"
		sv.normal("C"
		sv.subtree("D"
		sv.setStart(C);
		sv.validate();
	}

	@Test
	public void testDeleteSubtreeOnBranch() throws Exception {
		RevCommit D = commit(tree(file("b"
		RevCommit A = commit(
				subtreeAdd("suba"
						.getTree()
		RevCommit B = edit(A
		RevCommit C = commit(tree(file("b"
		rw.parseCommit(C);
		RevCommit E = commit(editTree(C.getTree()

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.normal("B"
		sv.normal("C"
		sv.subtree("D"
		sv.normal("E"
		sv.setStart(E);
		sv.validate();
	}

	@Test
	public void testModifySubtreeAtAdd() throws Exception {
		RevCommit A = commit(tree(file("a"
		RevCommit C = commit(tree(file("b1"
				file("b2"
		RevCommit B = edit(subtreeAdd("suba"
				file("suba/b2"
		rw.parseCommit(B);
		RevCommit D = edit(commit(C.getTree()
				file("b2"
		RevCommit E = edit(commit(B.getTree()
				file("suba/b2"
		RevCommit F = subtreeAdd("suba"

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.normal("B"
		sv.rewritten("B'"
		sv.subtree("C"
		sv.split("G"

		sv.setStart(B);
		sv.validate();

		sv.reset();
		sv.subtree("D"
		sv.split("H"
		sv.normal("E"
		sv.rewritten("E'"
		sv.setStart(E);
		sv.validate();

		sv.reset();
		sv.normal("F"
		sv.rewritten("F'"
		sv.setStart(F);
		sv.validate();

	}

	@Test
	public void testModifySubtree() throws Exception {
		RevCommit A = commit(tree(file("a"
		RevCommit B = commit(tree(file("b1"
				file("b2"
		RevCommit C = subtreeAdd("suba"
		RevCommit D = edit(C

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.subtree("B"
		sv.normal("C"
		sv.normal("D"
		sv.split("E"
		sv.rewritten("D'"
		sv.validate();

	}

	@Test
	public void testSplitSubtreeByPath() throws Exception {
		RevCommit A = commit(tree(file("a"
				file("suba/a1
		RevCommit B = edit(A
		RevCommit C = edit(B
				file("subb/b2"
		RevCommit D = edit(C

		SubtreeValidator sv = new SubtreeValidator(db
				"subb").setStart(D);
		sv.normal("A"
		sv.rewritten("A'"
		sv.normal("B"
		sv.rewritten("B'"
		sv.normal("C"
				.addSubtree("subb"
		sv.rewritten("C'"
		sv.normal("D"
				.addSubtree("subb"
		sv.rewritten("D'"
		sv.split("E"
		sv.split("F"
		sv.split("G"
		sv.split("H"
		sv.validate();
	}

	@Test
	public void testModifySubtreeOnBranch() throws Exception {
		RevCommit A = commit(tree(file("a"
		RevCommit F = commit(tree(file("b"
		RevCommit B = subtreeAdd("suba"

		SubtreeValidator sv = new SubtreeValidator(db
		sv.normal("A"
		sv.normal("B"
		sv.subtree("F"
		sv.validate();

		{
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()

			SubtreeValidator sv2 = sv.clone();
			sv2.normal("C"
			sv2.normal("D"
			sv2.normal("E"
			sv2.setStart(E);
			sv2.validate();
		}

		{
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()

			SubtreeValidator sv2 = sv.clone();
			sv2.normal("C"
			sv2.normal("D"
			sv2.rewritten("D'"
			sv2.normal("E"
			sv2.rewritten("E'"
			sv2.split("G"
			sv2.setStart(E);
			sv2.validate();
		}

		{
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()

			SubtreeValidator sv2 = sv.clone();
			sv2.normal("C"
			sv2.normal("D"
			sv2.rewritten("D'"
			sv2.normal("E"
			sv2.rewritten("E'"
			sv2.split("G"
			sv2.split("H"
			sv2.setStart(E);
			sv2.validate();
		}

		{
			SubtreeValidator sv2 = sv.clone();
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()
			sv2.normal("C"
			sv2.rewritten("C'"
			sv2.normal("D"
			sv2.rewritten("D'"
			sv2.normal("E"
			sv2.rewritten("E'"
			sv2.split("G"
			sv2.split("H"
			sv2.split("I"
			sv2.setStart(E);
			sv2.validate();
		}

		{
			SubtreeValidator sv2 = sv.clone();
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()
			sv2.normal("C"
			sv2.rewritten("C'"
			sv2.normal("D"
			sv2.rewritten("D'"
			sv2.normal("E"
			sv2.rewritten("E'"
			sv2.split("G"
			sv2.split("H"
			sv2.split("I"
			sv2.setStart(E);
			sv2.validate();
		}

		{
			SubtreeValidator sv2 = sv.clone();
			RevCommit C = edit(B
			RevCommit D = edit(B
			rw.parseCommit(C);
			RevCommit E = commit(
					editTree(C.getTree()
			sv2.normal("C"
			sv2.rewritten("C'"
			sv2.normal("D"
			sv2.rewritten("D'"
			sv2.normal("E"
			sv2.rewritten("E'"
			sv2.split("G"
			sv2.split("H"
			sv2.split("I"
			sv2.setStart(E);
			sv2.validate();
		}

	}

}
