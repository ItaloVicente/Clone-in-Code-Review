package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SymbolicRefCommandTest extends RepositoryTestCase {

    private Git git;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        git = new Git(db);
        writeTrashFile("Test.txt"
        git.add().addFilepattern("Test.txt").call();
        git.commit().setMessage("Initial commit").call();

        git.branchCreate().setName("test").call();
    }

    @Test
    public void testSimpleSetHeadBranch() throws Exception {
        String longTestBranchName = "refs/heads/test";

        git.symbolicRef().setName(Constants.HEAD).setNewRef(longTestBranchName).call();

        assertEquals(git.symbolicRef().setName(Constants.HEAD).call()
    }

    @Test
    public void testNewSymbolicRefCreationAndUpdate() throws Exception {
        String newRef = "refs/other/ref";
        String ref = "SOME_REF";

        git.symbolicRef().setName(ref).setNewRef(newRef).call();

        assertEquals(git.symbolicRef().setName(ref).call()

        String newRefOther = "refs/other-other/ref";

        git.symbolicRef().setName(ref).setNewRef(newRefOther).call();

        assertEquals(git.symbolicRef().setName(ref).call()
    }

    @Test
    public void testNotSymbolicRefRead() throws Exception {

        try {
            git.symbolicRef().setName("refs/heads/test").call();
            fail("Must throw exception on not a symbolic ref");
        } catch (Exception e) {
        }

    }
    
    @Test
    public void testNotSymbolicRefUpdate() throws Exception {

        try {
            git.symbolicRef().setName("refs/heads/test").setNewRef("refs/other/ref").call();
            fail("Must throw exception on not a symbolic ref");
        } catch (Exception e) {
        }

    }
}
