
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.jgit.lib.ObjectId;

class FetchHeadRecord {
	ObjectId newValue;

	boolean notForMerge;

	String sourceName;

	URIish sourceURI;

	void write(Writer pw) throws IOException {
		final String type;
		final String name;
		if (sourceName.startsWith(R_HEADS)) {
			name = sourceName.substring(R_HEADS.length());
		} else if (sourceName.startsWith(R_TAGS)) {
			name = sourceName.substring(R_TAGS.length());
		} else if (sourceName.startsWith(R_REMOTES)) {
			name = sourceName.substring(R_REMOTES.length());
		} else {
			name = sourceName;
		}

		pw.write(newValue.name());
		pw.write('\t');
		if (notForMerge)
		pw.write('\t');
		pw.write(type);
		pw.write(name);
		pw.write(sourceURI.toString());
	}
}
