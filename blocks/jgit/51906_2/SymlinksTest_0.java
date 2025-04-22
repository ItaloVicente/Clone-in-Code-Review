package org.eclipse.jgit.api;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkingTreeOptionsTest extends LocalDiskRepositoryTestCase {
    private Repository createRepoWithNestedRepo() throws Exception {

        File gitdir = createUniqueTestGitDir(false);
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(gitdir);
        Repository db = builder.build();
        db.create();


        File nestedRepoPath = new File(db.getWorkTree()

        FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
        nestedBuilder.setWorkTree(nestedRepoPath);
        nestedBuilder.build().create();


        File file = new File(nestedRepoPath
        FileUtils.createNewFile(file);
        PrintWriter writer = new PrintWriter(file);
        writer.print("content");
        writer.close();

        return db;
    }
    @Test
    public void testAddFileWithNoGitLinks() throws Exception {
        Repository db = createRepoWithNestedRepo();
        WorkingTreeOptions options = db.getConfig().get(WorkingTreeOptions.KEY);
        options.setDirNoGitLinks(true);

        System.out.println("Calling add command");
        Git git = new Git(db);
        git.add().addFilepattern("sub/nested/a.txt").call();
        System.out.println("Back from calling add command");

        assertEquals(
                "[sub/nested/a.txt
                indexState(db
    }

    @Test
    public void testStatusWithNoGitLinks() throws Exception {
        Repository db = createRepoWithNestedRepo();
        WorkingTreeOptions options = db.getConfig().get(WorkingTreeOptions.KEY);
        options.setDirNoGitLinks(true);

        Git git = new Git(db);

        Status initialStatus = git.status().call();
        assertTrue(initialStatus.getAdded().isEmpty());

        git.add().addFilepattern("sub/nested/a.txt").call();

        Status status = git.status().call();
        assertTrue(status.getAdded().contains("sub/nested/a.txt"));
    }

    @Test
    public void testCommitFileWithNoGitLinks() throws Exception {
        Repository db = createRepoWithNestedRepo();
        WorkingTreeOptions options = db.getConfig().get(WorkingTreeOptions.KEY);
        options.setDirNoGitLinks(true);

        Git git = new Git(db);

        git.add().addFilepattern("sub/nested/a.txt").call();
        RevCommit commit = git.commit().setAuthor("false"

        DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
        df.setRepository(db);
        df.setDiffComparator(RawTextComparator.DEFAULT);
        DiffEntry diff = df.scan(null

        assertEquals("sub/nested/a.txt"
        assertEquals("100644"
        assertEquals("ADD"
    }
}
