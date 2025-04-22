package org.eclipse.ui.tests.smartimport.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class ImportedProject {

	private String projectName;
	private List<String> importedAs;
	private String relativePath;

	public ImportedProject(String projectName, String relativePath) {
		this.projectName = projectName;
		this.relativePath = relativePath;
		importedAs = new ArrayList<>();
	}

	public void addImportedAs(String type){
		importedAs.add(type);
	}

	public String getProjectName() {
		return projectName;
	}

	public List<String> getImportedAsList() {
		return importedAs;
	}

	public String getRelativePath() {
		return relativePath;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImportedProject){
			ImportedProject project = (ImportedProject) obj;
			if (project.getProjectName().equals(projectName) && project.getRelativePath().equals(relativePath)){
				return project.getImportedAsList().equals(this.getImportedAsList());
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProjectName: "+projectName+", ");
		sb.append("relative path: \""+relativePath+"\", ");
		sb.append(importedAs.stream().collect(Collectors.joining(",")));
		return sb.toString();
	}

}
