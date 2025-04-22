        Git git = new Git(db);

        writeTrashFile("a", "first line\nsec. line\nthird line\n");
        git.add().addFilepattern("a").call();
        RevCommit firstCommit = git.commit().setMessage("create a").call();

        writeTrashFile("a", "first line\nsec. line\nthird line\nfourth line\n");
        git.add().addFilepattern("a").call();
        RevCommit enlargingA = git.commit().setMessage("enlarged a").call();

        writeTrashFile("a",
                "first line\nsecond line\nthird line\nfourth line\n");
        git.add().addFilepattern("a").call();
        RevCommit fixingA = git.commit().setMessage("fixed a").call();

        git.branchCreate().setName("side").setStartPoint(firstCommit).call();
        checkoutBranch("refs/heads/side");

        writeTrashFile("b", "nothing to do with a");
        git.add().addFilepattern("b").call();
        git.commit().setMessage("create b").call();

        CherryPickResult result = git.cherryPick().include(enlargingA).include(fixingA).call();
        assertEquals(CherryPickResult.CherryPickStatus.OK, result.getStatus());

        Iterator<RevCommit> history = git.log().call().iterator();
        assertEquals("fixed a", history.next().getFullMessage());
        assertEquals("enlarged a", history.next().getFullMessage());
        assertEquals("create b", history.next().getFullMessage());
        assertEquals("create a", history.next().getFullMessage());
        assertFalse(history.hasNext());
