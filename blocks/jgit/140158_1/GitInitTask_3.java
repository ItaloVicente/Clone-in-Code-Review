package org.eclipse.jgit.ant.tasks;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.URIish;

public class GitCloneTask extends Task {

	private String uri;
	private File destination;
	private boolean bare;
	private String branch = Constants.HEAD;

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setDest(File destination) {
		this.destination = destination;
	}

	public void setBare(boolean bare) {
		this.bare = bare;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public void execute() throws BuildException {
		log("Cloning repository " + uri);

		CloneCommand clone = Git.cloneRepository();
		try {
			clone.setURI(uri).setDirectory(destination).setBranch(branch).setBare(bare);
			clone.call().getRepository().close();
		} catch (GitAPIException | JGitInternalException e) {
			log("Could not clone repository: " + e
			throw new BuildException("Could not clone repository: " + e.getMessage()
		}
	}
}
