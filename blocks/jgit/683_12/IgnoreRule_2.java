package org.eclipse.jgit.ignore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IgnoreNode {
	private File baseDir;
	private File secondaryFile;
	private ArrayList<IgnoreRule> rules;
	private boolean matched;
	private long lastModified;

	public IgnoreNode(File baseDir) {
		this.baseDir = baseDir;
		rules = new ArrayList<IgnoreRule>();
		secondaryFile = null;
		lastModified = 0l;
	}

	private void parse() throws IOException {
		if (secondaryFile != null && secondaryFile.exists())
			parse(secondaryFile);

		parse(new File(baseDir.getAbsolutePath()
	}

	private void parse(File targetFile) throws IOException {
		if (!targetFile.exists()) {
			return;
		}
		BufferedReader br = new BufferedReader(new FileReader(targetFile));
		String txt;
		try {
			while ((txt = br.readLine()) != null) {
				txt = txt.trim();
				if (txt.length() > 0 && !txt.startsWith("#"))
					rules.add(new IgnoreRule(txt));
			}
		} finally {
			br.close();
		}
	}

	public String getBaseDir() {
		return baseDir.getAbsolutePath();
	}


	public ArrayList<IgnoreRule> getRules() {
		return rules;
	}


	public boolean isIgnored(String target) throws IOException {
		matched = false;
		File targetFile = new File(target);
		String tar = target.replaceFirst(getBaseDir()

		if (tar.length() == target.length())
			return false;

		if (rules.isEmpty()) {
			parse();
		}
		if (rules.isEmpty())
			return false;


		int i;
		for (i = rules.size() -1; i > -1; i--) {
			matched = rules.get(i).isMatch(tar
			if (matched)
				break;
		}

		if (i > -1 && rules.get(i) != null)
			return rules.get(i).getResult();

		return false;
	}

	public boolean wasMatched() {
		return matched;
	}

	public void addSecondarySource(File f) {
		secondaryFile = f;
	}

	public void clear() {
		rules.clear();
	}

	public void setLastModified(long val) {
		lastModified = val;
	}

	public long getLastModified() {
		return lastModified;
	}
}
