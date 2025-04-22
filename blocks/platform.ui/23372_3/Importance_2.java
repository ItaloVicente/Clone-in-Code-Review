package org.eclipse.e4.demo.cssbridge.model;

public enum FolderType {
	Inbox("Inbox"), Drafts("Drafts"), Sent("Sent");

	private String name;

	private FolderType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
