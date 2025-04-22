package org.eclipse.jgit.niofs.internal.daemon.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.RefFilter;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

public class GitUploadCommand extends BaseGitCommand {

	private UploadPackFactory<BaseGitCommand> uploadPackFactory;

	public GitUploadCommand(final String command
			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver
			final UploadPackFactory uploadPackFactory
		super(command
		this.uploadPackFactory = uploadPackFactory;
	}

	@Override
	protected String getCommandName() {
		return "git-upload-pack";
	}

	@Override
	protected void execute(final Repository repository
			final OutputStream err) {
		try {
			final UploadPack up = uploadPackFactory.create(this

			final PackConfig config = new PackConfig(repository);
			config.setCompressionLevel(Deflater.BEST_COMPRESSION);
			up.setPackConfig(config);

			if (up.getRefFilter() == RefFilter.DEFAULT) {
				up.setRefFilter(new HiddenBranchRefFilter());
			}

			up.upload(in
		} catch (final Exception ignored) {
		}
	}
}
