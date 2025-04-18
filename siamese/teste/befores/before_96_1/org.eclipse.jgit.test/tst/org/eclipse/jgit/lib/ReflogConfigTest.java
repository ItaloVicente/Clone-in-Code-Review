/*
 * Copyright (C) 2009, Christian Halstrick <christian.halstrick@sap.com>
 * Copyright (C) 2009, Christian Halstrick, Matthias Sohn, SAP AG
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eclipse.jgit.lib;

import java.io.IOException;

public class ReflogConfigTest extends RepositoryTestCase {
	public void testlogAllRefUpdates() throws Exception {
		long commitTime = 1154236443000L;
		int tz = -4 * 60;

		// check that there are no entries in the reflog and turn off writing
		// reflogs
		assertTrue("there should be no entries in reflog", db.getReflogReader(
				Constants.HEAD).getReverseEntries().size() == 0);
		db.getConfig().setBoolean("core", null, "logallrefupdates", false);

		// do one commit and check that reflog size is 0: no reflogs should be
		// written
		final Tree t = new Tree(db);
		addFileToTree(t, "i-am-a-file", "and this is the data in me\n");
		commit(t, "A Commit\n", new PersonIdent(jauthor, commitTime, tz),
				new PersonIdent(jcommitter, commitTime, tz));
		commitTime += 100;
		assertTrue(
				"Reflog for HEAD still contain no entry",
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == 0);

		// set the logAllRefUpdates parameter to true and check it
		db.getConfig().setBoolean("core", null, "logallrefupdates", true);
		assertTrue(db.getConfig().getCore().isLogAllRefUpdates());

		// do one commit and check that reflog size is increased to 1
		addFileToTree(t, "i-am-another-file", "and this is other data in me\n");
		commit(t, "A Commit\n", new PersonIdent(jauthor, commitTime, tz),
				new PersonIdent(jcommitter, commitTime, tz));
		commitTime += 100;
		assertTrue(
				"Reflog for HEAD should contain one entry",
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == 1);

		// set the logAllRefUpdates parameter to false and check it
		db.getConfig().setBoolean("core", null, "logallrefupdates", false);
		assertFalse(db.getConfig().getCore().isLogAllRefUpdates());

		// do one commit and check that reflog size is 2
		addFileToTree(t, "i-am-anotheranother-file",
				"and this is other other data in me\n");
		commit(t, "A Commit\n", new PersonIdent(jauthor, commitTime, tz),
				new PersonIdent(jcommitter, commitTime, tz));
		assertTrue(
				"Reflog for HEAD should contain two entries",
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == 2);
	}

	private void addFileToTree(final Tree t, String filename, String content)
			throws IOException {
		FileTreeEntry f = t.addFile(filename);
		writeTrashFile(f.getName(), content);
		t.accept(new WriteTree(trash, db), TreeEntry.MODIFIED_ONLY);
	}

	private void commit(final Tree t, String commitMsg, PersonIdent author,
			PersonIdent committer) throws IOException {
		final Commit commit = new Commit(db);
		commit.setAuthor(author);
		commit.setCommitter(committer);
		commit.setMessage(commitMsg);
		commit.setTree(t);
		ObjectWriter writer = new ObjectWriter(db);
		commit.setCommitId(writer.writeCommit(commit));

		int nl = commitMsg.indexOf('\n');
		final RefUpdate ru = db.updateRef(Constants.HEAD);
		ru.setNewObjectId(commit.getCommitId());
		ru.setRefLogMessage("commit : "
				+ ((nl == -1) ? commitMsg : commitMsg.substring(0, nl)), false);
		ru.forceUpdate();
	}
}
