package org.eclipse.jgit.gitrepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.Repository;

public class RepoProject implements Comparable<RepoProject> {
	final String name;
	final String path;
	final String revision;
	final String remote;
	final Set<String> groups;
	final List<CopyFile> copyfiles;
	String url;
	String defaultRevision;

	public static class CopyFile {
		final Repository repo;
		final String path;
		final String src;
		final String dest;

		public CopyFile(Repository repo
			this.repo = repo;
			this.path = path;
			this.src = src;
			this.dest = dest;
		}

		public void copy() throws IOException {
			File srcFile = new File(repo.getWorkTree()
			File destFile = new File(repo.getWorkTree()
			FileInputStream input = new FileInputStream(srcFile);
			try {
				FileOutputStream output = new FileOutputStream(destFile);
				try {
					FileChannel channel = input.getChannel();
					output.getChannel().transferFrom(channel
				} finally {
					output.close();
				}
			} finally {
				input.close();
			}
		}
	}

	public RepoProject(String name
			String remote
		this.name = name;
		if (path != null)
			this.path = path;
		else
			this.path = name;
		this.revision = revision;
		this.remote = remote;
		this.groups = new HashSet<String>();
		if (groups != null && groups.length() > 0)
			this.groups.addAll(Arrays.asList(groups.split("
			return path;
		else
	}

	public boolean isAncestorOf(RepoProject that) {
		return that.getPathWithSlash().startsWith(this.getPathWithSlash());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RepoProject) {
			RepoProject that = (RepoProject) o;
			return this.getPathWithSlash().equals(that.getPathWithSlash());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getPathWithSlash().hashCode();
	}

	@Override
	public int compareTo(RepoProject that) {
		return this.getPathWithSlash().compareTo(that.getPathWithSlash());
	}
}

