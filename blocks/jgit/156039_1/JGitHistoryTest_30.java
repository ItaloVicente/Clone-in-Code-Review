
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitGetCommonAncestorCommitTest extends AbstractTestInfra {

    private Git git;

    private static final String MASTER_BRANCH = "master";
    private static final String DEVELOP_BRANCH = "develop";

    @Before
    public void setup() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder

        git = new CreateRepository(gitSource).execute().get();
    }

    @Test
    public void successTest() throws IOException {
        commit(git

        RevCommit expectedCommonAncestorCommit = git.getLastCommit(MASTER_BRANCH);

        new CreateBranch((GitImpl) git

        commit(git
        commit(git

        commit(git
        commit(git

        RevCommit actualCommonAncestorCommit = git.getCommonAncestorCommit(MASTER_BRANCH
                                                                           DEVELOP_BRANCH);

        assertThat(actualCommonAncestorCommit.getName()).isEqualTo(expectedCommonAncestorCommit.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidBranchTest() {
        git.getCommonAncestorCommit(MASTER_BRANCH
                                    "invalid-branch");
    }
}
