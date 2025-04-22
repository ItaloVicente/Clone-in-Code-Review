package org.eclipse.jgit.gitrepo;

import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.gitrepo.RepoCommand.ManifestErrorException;
import org.eclipse.jgit.gitrepo.RepoProject.CopyFile;
import org.eclipse.jgit.gitrepo.RepoProject.LinkFile;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

class RegularSuperprojectWriter {

	private Repository repo;

	private ProgressMonitor monitor;

	RegularSuperprojectWriter(Repository repo
		this.repo = repo;
		this.monitor = monitor;
	}

	RevCommit write(List<RepoProject> repoProjects)
			throws ManifestErrorException {
		try (Git git = new Git(repo)) {
			for (RepoProject proj : repoProjects) {
				addSubmodule(proj.getName()
						proj.getRevision()
						proj.getLinkFiles()
			}
			return git.commit().setMessage(RepoText.get().repoCommitMessage)
					.call();
		} catch (GitAPIException | IOException e) {
			throw new ManifestErrorException(e);
		}
	}

	private void addSubmodule(String name
			String revision
			Git git) throws GitAPIException
		assert (!repo.isBare());
		assert (git != null);
		if (!linkfiles.isEmpty()) {
			throw new UnsupportedOperationException(
					JGitText.get().nonBareLinkFilesNotSupported);
		}

		SubmoduleAddCommand add = git.submoduleAdd().setName(name).setPath(path)
				.setURI(url);
		if (monitor != null)
			add.setProgressMonitor(monitor);

		Repository subRepo = add.call();
		if (revision != null) {
			try (Git sub = new Git(subRepo)) {
				sub.checkout().setName(findRef(revision
			}
			subRepo.close();
			git.add().addFilepattern(path).call();
		}
		for (CopyFile copyfile : copyfiles) {
			copyfile.copy();
			git.add().addFilepattern(copyfile.dest).call();
		}
	}

	private static String findRef(String ref
			throws IOException {
		if (!ObjectId.isId(ref)) {
			if (r != null)
				return r.getName();
		}
		return ref;
	}
}
