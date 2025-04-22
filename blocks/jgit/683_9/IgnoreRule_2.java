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

	public IgnoreNode(File baseDir) {
		this.baseDir = baseDir;
		rules = new ArrayList<IgnoreRule>();
		secondaryFile = null;
	}

	private void parse() throws IOException {
		if (secondaryFile != null && secondaryFile.exists())
			parse(secondaryFile);

		parse(new File(baseDir.getAbsolutePath() + "/.gitignore"));
	}

	private void parse(File targetFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(targetFile));
		String txt;
		try {
			while ((txt = br.readLine()) != null) {
				txt = txt.trim();
				if (txt.length() > 0 && !txt.startsWith("#"))
					rules.add(new IgnoreRule(txt));
			}
		} catch (IOException e) {
			throw e;
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


	public boolean isIgnored(File targetFile) throws IOException {
		String target = targetFile.getAbsolutePath();
		if (!target.startsWith(baseDir.getAbsolutePath()))
			return false;

		if (rules.isEmpty()) {
			try {
				parse();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (rules.isEmpty())
			return false;

		matched = false;
		String tar = target.replaceFirst(baseDir.getAbsolutePath()
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

	public void addSecondarySource(File f) throws IOException {
		secondaryFile = f;
	}

	public void clear() {
		rules.clear();
	}
}
