package org.eclipse.jgit.errors;

import org.eclipse.jgit.internal.JGitText;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;

public class SearchForReuseTimeout extends IOException {
    private static final long serialVersionUID = 1L;

    public SearchForReuseTimeout(Duration timeout) {
        super(MessageFormat.format(JGitText.get().searchForReuseTimeout
                timeout.toSeconds()));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
