
package org.eclipse.jgit.internal.storage.commitgraph;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertThrows;

public class OutPutStreamTest {

    private static class CancelledTestMonitor implements ProgressMonitor {

        private boolean cancelled = false;

        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }

        @Override
        public void start(int totalTasks) {
        }

        @Override
        public void beginTask(String title
        }

        @Override
        public void update(int completed) {
        }

        @Override
        public void endTask() {
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }
    }

    @Test
    public void testCancelStream() throws Exception {
        CancelledTestMonitor m = new CancelledTestMonitor();
        CommitGraphOutputStream out = new CommitGraphOutputStream(m

        byte[] KB = new byte[1024];
        m.setCancelled(true);
        for (int i = 0; i < 128; i++) {
            out.write(KB);
        }

        assertThrows(JGitText.get().commitGraphWritingCancelled
                IOException.class
                    out.write(KB);
                });
    }
}
