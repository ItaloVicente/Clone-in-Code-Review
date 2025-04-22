package org.eclipse.jgit.util;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RemoteSession;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.io.MessageWriter;
import org.eclipse.jgit.util.io.StreamCopyThread;

public class SshSupport {

	public static String runSshCommand(URIish sshUri
			@Nullable CredentialsProvider provider
			int timeout) throws IOException
		RemoteSession session = null;
		Process process = null;
		StreamCopyThread errorThread = null;
		StreamCopyThread outThread = null;
		CommandFailedException failure = null;
		@SuppressWarnings("resource")
		MessageWriter stderr = new MessageWriter();
		try (MessageWriter stdout = new MessageWriter()) {
			session = SshSessionFactory.getInstance().getSession(sshUri
					provider
			process = session.exec(command
			errorThread = new StreamCopyThread(process.getErrorStream()
					stderr.getRawStream());
			errorThread.start();
			outThread = new StreamCopyThread(process.getInputStream()
					stdout.getRawStream());
			outThread.start();
			try {
				if (process.waitFor() == 0) {
					return stdout.toString();
				} else {
				}
			} catch (InterruptedException e) {
			}
		} finally {
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}
			if (outThread != null) {
				try {
					outThread.halt();
				} catch (InterruptedException e) {
				} finally {
					outThread = null;
				}
			}
			if (process != null) {
				if (process.exitValue() != 0) {
					failure = new CommandFailedException(process.exitValue()
							MessageFormat.format(
							JGitText.get().sshCommandFailed
							stderr.toString()));
				}
				process.destroy();
			}
			stderr.close();
			if (session != null) {
				SshSessionFactory.getInstance().releaseSession(session);
			}
			if (failure != null) {
				throw failure;
			}
		}
	}

}
