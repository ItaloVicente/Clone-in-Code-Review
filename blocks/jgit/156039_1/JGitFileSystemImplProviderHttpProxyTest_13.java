
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.Map;

import org.eclipse.jgit.hooks.PreCommitHook;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.HOOK_DIR;

public class JGitFileSystemImplProviderHookTest extends AbstractTestInfra {

    @Override
    public Map<String
        Map<String
        try {
            final File hooksDir = createTempDirectory();
            gitPrefs.put(HOOK_DIR
                         hooksDir.getAbsolutePath());

            writeMockHook(hooksDir
                          "post-commit");
            writeMockHook(hooksDir
                          PreCommitHook.NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gitPrefs;
    }

    @Test
    public void testInstalledHook() throws IOException {

        final FileSystem fs = provider.newFileSystem(newRepo
                                                     EMPTY_ENV);

        assertThat(fs).isNotNull();

        if (fs instanceof JGitFileSystemImpl) {
            File[] hooks = new File(((JGitFileSystemImpl) fs).getGit().getRepository().getDirectory()
                                    "hooks").listFiles();
            assertThat(hooks).isNotEmpty().isNotNull();
            assertThat(hooks.length).isEqualTo(2);

            boolean foundPreCommitHook = false;
            boolean foundPostCommitHook = false;
            for (File hook : hooks) {
                if (hook.getName().equals("pre-commit")) {
                    foundPreCommitHook = hook.canExecute();
                } else if (hook.getName().equals("post-commit")) {
                    foundPostCommitHook = hook.canExecute();
                }
            }
            assertThat(foundPreCommitHook).isTrue();
            assertThat(foundPostCommitHook).isTrue();
        }
    }

    @Test
    public void testExecutedPostCommitHook() throws IOException {
        testHook("hook-repo-name-executed"
                 "post-commit"
                 true);
    }

    @Test
    public void testNotSupportedPreCommitHook() throws IOException {
        testHook("hook-repo-name-executed-pre-commit"
                 "pre-commit"
                 false);
    }
}
