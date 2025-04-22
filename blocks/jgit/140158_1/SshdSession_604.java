package org.eclipse.jgit.transport.sshd;

@FunctionalInterface
public interface SessionCloseListener {

	void sessionClosed(SshdSession session);
}
