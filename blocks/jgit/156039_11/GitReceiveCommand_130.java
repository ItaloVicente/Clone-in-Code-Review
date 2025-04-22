package org.eclipse.jgit.niofs.internal.daemon.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

import org.apache.sshd.common.channel.ChannelOutputStream;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.session.ServerSession;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.security.User;
import org.eclipse.jgit.niofs.internal.util.DescriptiveRunnable;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public abstract class BaseGitCommand implements Command

	public final static Session.AttributeKey<User> SUBJECT_KEY = new Session.AttributeKey<User>();

	protected final String command;
	protected final String repositoryName;
	protected final RepositoryResolver repositoryResolver;
	private final ExecutorService executorService;

	private InputStream in;
	private OutputStream out;
	private OutputStream err;
	private ExitCallback callback;
	private User user;

	public BaseGitCommand(final String command
			final ExecutorService executorService) {
		this.command = command;
		this.repositoryName = buildRepositoryName(command);
		this.repositoryResolver = repositoryResolver;
		this.executorService = executorService;
	}

	private String buildRepositoryName(String command) {
		int start = getCommandName().length() + 2;
		final String temp = command.substring(start);
		return temp.substring(0
	}

	protected abstract String getCommandName();

	@Override
	public void setInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public void setOutputStream(OutputStream out) {
		this.out = out;
		if (out instanceof ChannelOutputStream) {
			((ChannelOutputStream) out).setNoDelay(true);
		}
	}

	@Override
	public void setErrorStream(OutputStream err) {
		this.err = err;
		if (err instanceof ChannelOutputStream) {
			((ChannelOutputStream) err).setNoDelay(true);
		}
	}

	@Override
	public void setExitCallback(ExitCallback callback) {
		this.callback = callback;
	}

	@Override
	public void start(ChannelSession channel
		executorService.execute(new DescriptiveRunnable() {
			@Override
			public String getDescription() {
				return "Git Command [" + getClass().getName() + "]";
			}

			@Override
			public void run() {
				BaseGitCommand.this.run();
			}
		});
	}

	@Override
	public void run() {
		try {
			final Repository repository = openRepository(repositoryName);
			execute(repository
		} catch (final Throwable e) {
			try {
				err.write(e.getMessage().getBytes());
			} catch (IOException ignored) {
			}
		}
		if (callback != null) {
			callback.onExit(0);
		}
	}

	private Repository openRepository(String name) throws ServiceMayNotContinueException {
		name = name.replace('\\'

		if (!name.startsWith("/")) {
			return null;
		}

		try {
			return repositoryResolver.open(this
		} catch (RepositoryNotFoundException e) {
			return null;
		} catch (ServiceNotAuthorizedException e) {
			return null;
		} catch (ServiceNotEnabledException e) {
			return null;
		}
	}

	protected abstract void execute(final Repository repository
			final OutputStream err);

	@Override
	public void destroy(final ChannelSession channel) {
	}

	public User getUser() {
		return user;
	}

	@Override
	public void setSession(final ServerSession session) {
		this.user = session.getAttribute(BaseGitCommand.SUBJECT_KEY);
	}
}
