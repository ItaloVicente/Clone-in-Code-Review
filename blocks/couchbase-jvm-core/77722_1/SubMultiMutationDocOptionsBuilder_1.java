
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubMultiMutationDocOptionsBuilder {
	private boolean createDocument;

	public static SubMultiMutationDocOptionsBuilder builder() {
		return new SubMultiMutationDocOptionsBuilder();
	}

	public SubMultiMutationDocOptionsBuilder createDocument(boolean createDocument) {
		this.createDocument = createDocument;
		return this;
	}

	public boolean createDocument() {
		return this.createDocument;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(" \"createDocument\":" + createDocument);
		sb.append("}");
		return sb.toString();
	}
}
