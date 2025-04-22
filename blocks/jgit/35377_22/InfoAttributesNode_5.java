package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

public class GlobalAttributesNode extends AttributesNode {
	final Repository repository;

	public GlobalAttributesNode(Repository repository) {
		this.repository = repository;
	}

	public AttributesNode load() throws IOException {
		AttributesNode r = new AttributesNode();

		FS fs = repository.getFS();
		String path = repository.getConfig().get(CoreConfig.KEY)
				.getAttributesFile();
		if (path != null) {
			File attributesFile;
				attributesFile = fs.resolve(fs.userHome()
						path.substring(2));
			} else {
				attributesFile = fs.resolve(null
			}
			FileRepository.AttributesNodeProviderImpl.loadRulesFromFile(r
		}
		return r.getRules().isEmpty() ? null : r;
	}
}
