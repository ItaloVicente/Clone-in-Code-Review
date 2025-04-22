package org.eclipse.jgit.gitrepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.Repository;

public class RepoProject implements Comparable<RepoProject> {
	private final String name;
	private final String path;
	private final String revision;
	private final String remote;
	private final Set<String> groups;
	private final List<CopyFile> copyfiles;
	private final List<LinkFile> linkfiles;
	private String recommendShallow;
	private String url;
	private String defaultRevision;

	public static class ReferenceFile {
		final Repository repo;
		final String path;
		final String src;
		final String dest;

		public ReferenceFile(Repository repo
			this.repo = repo;
			this.path = path;
			this.src = src;
			this.dest = dest;
		}
	}

	public static class CopyFile extends ReferenceFile {
		public CopyFile(Repository repo
			super(repo
		}

		public void copy() throws IOException {
			File srcFile = new File(repo.getWorkTree()
			File destFile = new File(repo.getWorkTree()
			try (FileInputStream input = new FileInputStream(srcFile);
					FileOutputStream output = new FileOutputStream(destFile)) {
				FileChannel channel = input.getChannel();
				output.getChannel().transferFrom(channel
			}
			destFile.setExecutable(srcFile.canExecute());
		}
	}

	public static class LinkFile extends ReferenceFile {
		public LinkFile(Repository repo
			super(repo
		}
	}

	public RepoProject(String name
			String remote
			String recommendShallow) {
		if (name == null) {
			throw new NullPointerException();
		}
		this.name = name;
		if (path != null)
			this.path = path;
		else
			this.path = name;
		this.revision = revision;
		this.remote = remote;
		this.groups = groups;
		this.recommendShallow = recommendShallow;
		copyfiles = new ArrayList<>();
		linkfiles = new ArrayList<>();
	}

	public RepoProject(String name
			String remote
		this(name
		if (groupsParam != null && groupsParam.length() > 0)
			this.setGroups(groupsParam);
	}

	public RepoProject setUrl(String url) {
		this.url = url;
		return this;
	}

	public RepoProject setGroups(String groupsParam) {
		this.groups.clear();
		this.groups.addAll(Arrays.asList(groupsParam.split("
	public String getRecommendShallow() {
		return recommendShallow;
	}

	public void setRecommendShallow(String recommendShallow) {
		this.recommendShallow = recommendShallow;
	}

	public void addCopyFile(CopyFile copyfile) {
		copyfiles.add(copyfile);
	}

	public void addCopyFiles(Collection<CopyFile> copyFiles) {
		this.copyfiles.addAll(copyFiles);
	}

	public void clearCopyFiles() {
		this.copyfiles.clear();
	}

	public void addLinkFile(LinkFile linkfile) {
		linkfiles.add(linkfile);
	}

	public void addLinkFiles(Collection<LinkFile> linkFiles) {
		this.linkfiles.addAll(linkFiles);
	}

	public void clearLinkFiles() {
		this.linkfiles.clear();
	}

	private String getPathWithSlash() {
			return path;
		else
	}

	public boolean isAncestorOf(RepoProject that) {
		return isAncestorOf(that.getPathWithSlash());
	}

	public boolean isAncestorOf(String thatPath) {
		return thatPath.startsWith(getPathWithSlash());
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

