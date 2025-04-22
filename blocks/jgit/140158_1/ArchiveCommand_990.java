package org.eclipse.jgit.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplyResult {

	private List<File> updatedFiles = new ArrayList<>();

	public ApplyResult addUpdatedFile(File f) {
		updatedFiles.add(f);
		return this;

	}

	public List<File> getUpdatedFiles() {
		return updatedFiles;
	}
}
