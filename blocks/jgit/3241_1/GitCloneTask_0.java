package org.eclipse.jgit.ant.tasks;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitCheckoutTask extends Task {

	private File src;
	private String branch;
	private boolean createBranch;
	private boolean force;

	public void setSrc(File src) {
		this.src = src;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setCreateBranch(boolean createBranch) {
		this.createBranch = createBranch;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	@Override
	public void execute() throws BuildException {
		CheckoutCommand checkout;
		try {
			Repository repo = new FileRepositoryBuilder().readEnvironment().findGitDir(src).build();
			checkout = new Git(repo).checkout();
		} catch (IOException e) {
			throw new BuildException("Could not access repository " + src
		}
		try {
			checkout.setCreateBranch(createBranch).setForce(force).setName(branch);
			checkout.call();
		} catch (Exception e) {
			throw new BuildException("Could not checkout repository " + src
		}
	}
	
	public static void main(String[] args) {
		GitCheckoutTask task = new GitCheckoutTask();
		task.setSrc(new File("tmp/clone/repo"));
		task.setBranch("origin/recorder-spike");
		task.execute();
	}
}
