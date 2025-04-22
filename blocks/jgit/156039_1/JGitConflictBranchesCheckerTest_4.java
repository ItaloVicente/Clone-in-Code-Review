
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JGitCloneTest extends AbstractTestInfra {

    private static final String
            TARGET_GIT = "target/target"
            SOURCE_GIT = "source/source";

    @Test
    public void testToCloneSuccess() throws IOException
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

        final Git cloned = new Clone(gitTarget
                                     gitSource.getAbsolutePath()
                                     false
                                     null
                                     CredentialsProvider.getDefault()
                                     null
                                     null
                                     true).execute().get();

        assertThat(cloned).isNotNull();

        assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
        assertEquals(new ListRefs(cloned.getRepository()).execute().size()
                     new ListRefs(origin.getRepository()).execute().size());

        assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
        assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
    }

    @Test
    public void cloneShouldOnlyWorksWithEmptyRepos() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

        final Git cloned = new Clone(gitTarget
                                     gitSource.getAbsolutePath()
                                     false
                                     null
                                     CredentialsProvider.getDefault()
                                     null
                                     null
                                     true).execute().get();

        assertThat(cloned).isNotNull();

        assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
        assertEquals(new ListRefs(cloned.getRepository()).execute().size()
                     new ListRefs(origin.getRepository()).execute().size());

        assertThatThrownBy(() -> new Clone(gitTarget
                                           gitSource.getAbsolutePath()
                                           false
                                           null
                                           CredentialsProvider.getDefault()
                                           null
                                           null
                                           true).execute().get())
                .isInstanceOf(Clone.CloneException.class);
    }

    @Test
    public void testCloneWithHookDir() throws IOException
        final File hooksDir = createTempDirectory();

        writeMockHook(hooksDir
        writeMockHook(hooksDir

        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

        final Git cloned = new Clone(gitTarget
                                     gitSource.getAbsolutePath()
                                     false
                                     null
                                     CredentialsProvider.getDefault()
                                     null
                                     hooksDir
                                     true).execute().get();

        assertThat(cloned).isNotNull();

        assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
        assertEquals(new ListRefs(cloned.getRepository()).execute().size()
                     new ListRefs(origin.getRepository()).execute().size());

        assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
        assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");

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

    private Git setupGitRepo(File gitSource
        final Git origin = new CreateRepository(gitSource

        new Commit(origin
                   "user_branch"
                   "name"
                   "name@example.com"
                   "commit!"
                   null
                   null
                   false
                   new HashMap<String
                       put("file2.txt"
                           tempFile("temp2222"));
                   }}).execute();
        new Commit(origin
                   "master"
                   "name"
                   "name@example.com"
                   "commit"
                   null
                   null
                   false
                   new HashMap<String
                       put("file.txt"
                           tempFile("temp"));
                   }}).execute();
        return origin;
    }

    @Test
    public void cloneNotMirrorRepoConfigTest() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

        boolean isMirror = false;
        boolean sslVerify = true;
        final Git clonedNotMirror = new Clone(gitTarget
                                              gitSource.getAbsolutePath()
                                              isMirror
                                              null
                                              CredentialsProvider.getDefault()
                                              null
                                              null
                                              sslVerify).execute().get();

        assertThat(clonedNotMirror).isNotNull();

        StoredConfig config = clonedNotMirror.getRepository().getConfig();

        assertNotEquals(Clone.REFS_MIRRORED
        assertNull(config.getString("remote"
        assertEquals(gitSource.getAbsolutePath()

        boolean missingDefaultValue = true;
        assertEquals(missingDefaultValue
    }

    @Test
    public void cloneMirrorRepoNoSSLVerifyConfigTest() throws IOException {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

        assertTrue(provider.config.isSslVerify());

        boolean isMirror = true;
        boolean sslVerify = false;
        final Git clonedMirror = new Clone(gitTarget
                                           gitSource.getAbsolutePath()
                                           isMirror
                                           null
                                           CredentialsProvider.getDefault()
                                           null
                                           null
                                           sslVerify).execute().get();

        assertThat(clonedMirror).isNotNull();

        StoredConfig config = clonedMirror.getRepository().getConfig();

        assertEquals(Clone.REFS_MIRRORED
        assertNull(config.getString("remote"
        assertEquals(gitSource.getAbsolutePath()
        assertEquals(sslVerify
    }

    @Test
    public void testCloneMultipleBranches() throws Exception {
        final File parentFolder = createTempDirectory();

        final File gitSource = new File(parentFolder
                                        SOURCE_GIT + ".git");

        final File gitTarget = new File(parentFolder
                                        TARGET_GIT + ".git");

        final Git origin = setupGitRepo(gitSource

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

        final Git cloned = new Clone(gitTarget
                                     gitSource.getAbsolutePath()
                                     false
                                     asList("master"
                                     CredentialsProvider.getDefault()
                                     null
                                     null
                                     false).execute().get();

        assertThat(cloned).isNotNull();
        final Set<String> clonedRefs = listRefs(cloned).stream()
                .map(ref -> ref.getName())
                .collect(toSet());
        assertThat(clonedRefs).hasSize(2);
        assertThat(clonedRefs).containsExactly("refs/heads/master"
    }
}
