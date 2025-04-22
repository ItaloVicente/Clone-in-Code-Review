package org.eclipse.jgit.ant.tasks;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;

public class GitInitTask extends Task {
	private File destination;
	private boolean bare;

	public void setDest(File dest) {
		this.destination = dest;
	}

	public void setBare(boolean bare) {
		this.bare = bare;
	}

	@Override
	public void execute() throws BuildException {
		if (bare) {
			log("Initializing bare repository at " + destination);
		} else {
			log("Initializing repository at " + destination);
		}
		try {
			InitCommand init = Git.init();
			init.setBare(bare).setDirectory(destination);
			init.call();
		} catch (Exception e) {
			throw new BuildException("Could not initialize repository"
		}
	}
}
