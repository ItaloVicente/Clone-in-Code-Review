package org.eclipse.jgit.ant.tasks;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.lib.Constants;

public class GitCloneTask extends Task {

	private String uri;
	private File dest;
	private boolean bare;
	private String branch = Constants.HEAD;

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setDest(File dest) {
		this.dest = dest;
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

		CloneCommand clone = new CloneCommand();
		
		clone.setURI(uri).setDirectory(dest).setBranch(branch).setBare(bare);
		try {
			clone.call();
		} catch (Exception e) {
			throw new BuildException("Could not clone repository: " + e.getMessage()
		}
	}
}
