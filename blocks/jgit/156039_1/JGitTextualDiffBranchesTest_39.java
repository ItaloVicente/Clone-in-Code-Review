
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.SubdirectoryClone;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class JGitSubdirectoryCloneTest extends AbstractTestInfra {

    private static final String TARGET_GIT = "target/target"
            SOURCE_GIT = "source/source";

    @Test
    public void cloneSubdirectorySingleBranch() throws Exception {
        final File parentFolder = createTempDirectory();

        final File sourceDir = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File targetDir = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = gitRepo(sourceDir);
        commit(origin
        commit(origin
        commit(origin

        final Git cloned = new SubdirectoryClone(targetDir
                                                 sourceDir.getAbsoluteFile().toURI().toString()
                                                 "dir1"
                                                 singletonList("master")
                                                 CredentialsProvider.getDefault()
                                                 null
                                                 null).execute();

        assertThat(origin.getRepository().getRemoteNames()).isEmpty();

        assertThat(cloned).isNotNull();
        assertThat(listRefs(cloned)).hasSize(1);

        final List<RevCommit> cloneCommits = getCommits(cloned
        assertThat(cloneCommits).hasSize(1);

        final RevCommit clonedCommit = cloneCommits.get(0);
        final RevCommit originCommit = getCommits(origin

        assertClonedCommitData(origin
    }

    @Test
    public void cloneSubdirectoryMultipleBranches() throws Exception {
        final File parentFolder = createTempDirectory();

        final File sourceDir = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File targetDir = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = gitRepo(sourceDir);
        commit(origin
               "master"
               "first"
               content("dir1/file.txt"
               content("dir2/file2.txt"
               content("file3.txt"

        branch(origin
        commit(origin
               "dev"
               "second"
               content("dir1/file.txt"
               content("file3.txt"

        branch(origin
        commit(origin
               "ignored"
               "third"
               content("dir1/file.txt"

        final Git cloned = new SubdirectoryClone(targetDir
                                                 sourceDir.getAbsoluteFile().toURI().toString()
                                                 "dir1"
                                                 asList("master"
                                                 CredentialsProvider.getDefault()
                                                 null
                                                 null).execute();

        assertThat(cloned).isNotNull();
        final Set<String> clonedRefs = listRefs(cloned).stream()
                .map(ref -> ref.getName())
                .collect(toSet());
        assertThat(clonedRefs).hasSize(2);
        assertThat(clonedRefs).containsExactly("refs/heads/master"

        {
            final List<RevCommit> cloneCommits = getCommits(cloned
            assertThat(cloneCommits).hasSize(1);
            assertClonedCommitData(origin
        }

        {
            final List<RevCommit> cloneCommits = getCommits(cloned
            assertThat(cloneCommits).hasSize(2);

            final List<RevCommit> originCommits = getCommits(origin
            assertClonedCommitData(origin
            assertClonedCommitData(origin
        }
    }

    @Test
    public void cloneSubdirectoryWithMergeCommit() throws Exception {
        final File parentFolder = createTempDirectory();

        final File sourceDir = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File targetDir = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = gitRepo(sourceDir);
        commit(origin
               "master"
               "first"
               content("dir1/file.txt"
               content("dir2/file2.txt"
               content("file3.txt"

        branch(origin
        commit(origin
               "dev"
               "second"
               content("dir1/file.txt"
               content("file3.txt"

        commit(origin
               "master"
               "another"
               content("dir1/file2.txt"

        mergeCommit(origin
                    "master"
                    "dev"
                    content("dir1/file.txt"
                    content("dir2/file2.txt"
                    content("file3.txt"

        final Git cloned = new SubdirectoryClone(targetDir
                                                 sourceDir.getAbsoluteFile().toURI().toString()
                                                 "dir1"
                                                 asList("master"
                                                 CredentialsProvider.getDefault()
                                                 null
                                                 null).execute();

        assertThat(cloned).isNotNull();
        final Set<String> clonedRefs = listRefs(cloned).stream()
                .map(ref -> ref.getName())
                .collect(toSet());
        assertThat(clonedRefs).hasSize(2);
        assertThat(clonedRefs).containsExactly("refs/heads/master"

        {
            final List<RevCommit> cloneCommits = getCommits(cloned

            final List<RevCommit> originCommits = getCommits(origin
            assertClonedCommitData(origin
            assertClonedCommitData(origin
            assertClonedCommitData(origin
            assertClonedCommitData(origin

            assertThat(cloneCommits.get(0).getParentCount()).isEqualTo(2);
            assertThat(cloneCommits.get(1).getParentCount()).isEqualTo(1);
            assertThat(cloneCommits.get(2).getParentCount()).isEqualTo(1);
            assertThat(cloneCommits.get(3).getParentCount()).isEqualTo(0);
        }

        {
            final List<RevCommit> cloneCommits = getCommits(cloned
            assertThat(cloneCommits).hasSize(2);

            final List<RevCommit> originCommits = getCommits(origin
            assertClonedCommitData(origin
            assertClonedCommitData(origin
        }
    }

    @Test
    public void cloneSubdirectoryWithHookDir() throws Exception {
        final File hooksDir = createTempDirectory();

        writeMockHook(hooksDir
        writeMockHook(hooksDir

        final File parentFolder = createTempDirectory();

        final File sourceDir = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File targetDir = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = gitRepo(sourceDir);
        commit(origin
        commit(origin
        commit(origin

        final Git cloned = new SubdirectoryClone(targetDir
                                                 sourceDir.getAbsoluteFile().toURI().toString()
                                                 "dir1"
                                                 singletonList("master")
                                                 CredentialsProvider.getDefault()
                                                 null
                                                 hooksDir).execute();

        assertThat(origin.getRepository().getRemoteNames()).isEmpty();

        assertThat(cloned).isNotNull();
        assertThat(listRefs(cloned)).hasSize(1);

        final List<RevCommit> cloneCommits = getCommits(cloned
        assertThat(cloneCommits).hasSize(1);

        final RevCommit clonedCommit = cloneCommits.get(0);
        final RevCommit originCommit = getCommits(origin

        assertClonedCommitData(origin

        boolean foundPreCommitHook = false;
        boolean foundPostCommitHook = false;
        File[] hooks = new File(cloned.getRepository().getDirectory()
        assertThat(hooks).isNotEmpty().isNotNull();
        assertThat(hooks.length).isEqualTo(2);
        for (File hook : hooks) {
            if (hook.getName().equals(PreCommitHook.NAME)) {
                foundPreCommitHook = hook.canExecute();
            } else if (hook.getName().equals(PostCommitHook.NAME)) {
                foundPostCommitHook = hook.canExecute();
            }
        }
        assertThat(foundPreCommitHook).isTrue();
        assertThat(foundPostCommitHook).isTrue();
    }

    private void assertClonedCommitData(final Git origin
                                        final String subdirectory
                                        final RevCommit clonedCommit
                                        final RevCommit originCommit) throws Exception {
        assertThat(clonedCommit.getFullMessage()).isEqualTo(originCommit.getFullMessage());

        final PersonIdent authorIdent = clonedCommit.getAuthorIdent();
        final PersonIdent commiterIdent = clonedCommit.getCommitterIdent();
        assertThat(authorIdent).isEqualTo(commiterIdent);
        assertThat(authorIdent.getName()).isEqualTo("name");
        assertThat(authorIdent.getEmailAddress()).isEqualTo("name@example.com");

        final ObjectId originDirId = findIdForPath(origin
        final ObjectId clonedTreeId = clonedCommit.getTree().getId();
        assertThat(clonedTreeId).isEqualTo(originDirId);
    }

    private ObjectId findIdForPath(final Git origin
        try (TreeWalk treeWalk = new TreeWalk(origin.getRepository())) {
            final int treeId = treeWalk.addTree(originMasterTip.getTree());
            treeWalk.setRecursive(false);
            final CanonicalTreeParser treeParser = treeWalk.getTree(treeId
            while (treeWalk.next()) {
                final String path = treeParser.getEntryPathString();
                if (path.equals(searchPath)) {
                    return treeParser.getEntryObjectId();
                }
            }
        }

        throw new AssertionError(String.format("Could not find path [%s] in commit [%s]."
    }

    private List<RevCommit> getCommits(final Git git
        List<RevCommit> commits = new ArrayList<>();
        try (RevWalk revWalk = new RevWalk(git.getRepository())) {
            final RevCommit branchTip = revWalk.parseCommit(git.getRepository().resolve(branch));
            revWalk.markStart(branchTip);
            revWalk.sort(RevSort.TOPO);
            final Iterator<RevCommit> iter = revWalk.iterator();
            while (iter.hasNext()) {
                commits.add(iter.next());
            }
        }
        return commits;
    }

    private Git gitRepo(File gitSource) throws IOException {
        return new CreateRepository(gitSource).execute().get();
    }

    private void mergeCommit(final Git origin
                             final String targetBranchName
                             final String sourceBranchName
                             final TestFile... testFiles) throws Exception {
        final Repository repo = origin.getRepository();
        final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repo);

        final ObjectId targetId = repo.resolve(targetBranchName);
        final ObjectId sourceId = repo.resolve(sourceBranchName);

        final DirCache dc = DirCache.newInCore();
        final DirCacheEditor editor = dc.editor();

        try (ObjectInserter inserter = repo.newObjectInserter()) {
            final ObjectId treeId = writeTestFilesToTree(dc
            final ObjectId commitId = writeCommit(inserter
            updateBranch(targetBranchName
        }
    }

    private void updateBranch(final String targetBranchName
                              final org.eclipse.jgit.api.Git git
                              final ObjectId commitId) throws Exception {
        git.branchCreate()
                .setName(targetBranchName)
                .setStartPoint(commitId.name())
                .setForce(true)
                .call();
    }

    private ObjectId writeCommit(final ObjectInserter inserter
        final CommitBuilder builder = new CommitBuilder();
        builder.setAuthor(new PersonIdent("name"
        builder.setCommitter(new PersonIdent("name"
        builder.setTreeId(commitTreeId);
        builder.setMessage("merge commit");
        builder.setParentIds(parentIds);

        final ObjectId commitId = inserter.insert(builder);
        return commitId;
    }

    private ObjectId writeTestFilesToTree(final DirCache dc
                                          final DirCacheEditor editor
                                          ObjectInserter inserter
                                          final TestFile... testFiles) throws Exception {
        for (TestFile data : testFiles) {
            writeBlob(editor
        }
        editor.finish();
        final ObjectId commitTreeId = dc.writeTree(inserter);
        return commitTreeId;
    }

    private void writeBlob(final DirCacheEditor editor
        final ObjectId blobId = inserter.insert(Constants.OBJ_BLOB
        editor.add(new PathEdit(data.path) {
            @Override
            public void apply(DirCacheEntry ent) {
                ent.setFileMode(FileMode.REGULAR_FILE);
                ent.setObjectId(blobId);
            }
        });
    }
}
