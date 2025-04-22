package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

public class InfoAttributesNode extends AttributesNode {
	final Repository repository;

	public InfoAttributesNode(Repository repository) {
		this.repository = repository;
	}

	public AttributesNode load() throws IOException {
		AttributesNode r = new AttributesNode();

		FS fs = repository.getFS();

		File attributes = fs.resolve(repository.getDirectory()
		FileRepository.AttributesNodeProviderImpl.loadRulesFromFile(r

		return r.getRules().isEmpty() ? null : r;
	}

}
