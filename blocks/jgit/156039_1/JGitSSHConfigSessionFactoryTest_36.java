
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitRevertMergeTest extends AbstractTestInfra {

    private Git git;

    private static final String MASTER_BRANCH = "master";
    private static final String DEVELOP_BRANCH = "develop";

    private static final List<String> TXT_FILES =
            Stream.of("file0"
                    .collect(Collectors.toList());

    private static final String[] COMMON_TXT_LINES = {"Line1"

    private String commonAncestorCommitId;

    @Before
    public void setup() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder

        git = new CreateRepository(gitSource).execute().get();

        commit(git
               content(TXT_FILES.get(0)
               content(TXT_FILES.get(1)
               content(TXT_FILES.get(2)

        new CreateBranch((GitImpl) git

        commit(git
               content(TXT_FILES.get(3)
               content(TXT_FILES.get(4)

        commonAncestorCommitId = git.getCommonAncestorCommit(DEVELOP_BRANCH
                                                             MASTER_BRANCH).getName();
    }

    @Test(expected = GitException.class)
    public void testInvalidSourceBranch() throws IOException {
        String mergeCommitId = doMerge();

        git.revertMerge("invalid-branch"
                        MASTER_BRANCH
                        commonAncestorCommitId
                        mergeCommitId);
    }

    @Test(expected = GitException.class)
    public void testInvalidTargetBranch() throws IOException {
        String mergeCommitId = doMerge();

        git.revertMerge(DEVELOP_BRANCH
                        "invalid-branch"
                        commonAncestorCommitId
                        mergeCommitId);
    }

    @Test
    public void testRevertFailedMergeIsNotLastTargetCommit() throws IOException {
        String mergeCommitId = doMerge();

        commit(git
               content(TXT_FILES.get(0)

        boolean result = git.revertMerge(DEVELOP_BRANCH
                                         MASTER_BRANCH
                                         commonAncestorCommitId
                                         mergeCommitId);

        assertThat(result).isFalse();
    }

    @Test
    public void testRevertFailedMergeParentTargetIsNotCommonAncestor() throws IOException {
        commit(git
               content(TXT_FILES.get(0)

        String mergeCommitId = doMerge();

        boolean result = git.revertMerge(DEVELOP_BRANCH
                                         MASTER_BRANCH
                                         commonAncestorCommitId
                                         mergeCommitId);

        assertThat(result).isFalse();
    }

    @Test
    public void testRevertFailedMergeSourceParentIsNotLastSourceCommit() throws IOException {
        String mergeCommitId = doMerge();

        commit(git
               content(TXT_FILES.get(0)

        boolean result = git.revertMerge(DEVELOP_BRANCH
                                         MASTER_BRANCH
                                         commonAncestorCommitId
                                         mergeCommitId);

        assertThat(result).isFalse();
    }

    @Test
    public void testRevertSucceeded() throws IOException {
        String mergeCommitId = doMerge();

        boolean result = git.revertMerge(DEVELOP_BRANCH
                                         MASTER_BRANCH
                                         commonAncestorCommitId
                                         mergeCommitId);

        assertThat(result).isTrue();
    }

    private String doMerge() throws IOException {
        git.merge(DEVELOP_BRANCH
                  MASTER_BRANCH
                  true);

        return git.getLastCommit(MASTER_BRANCH).getName();
    }
}
