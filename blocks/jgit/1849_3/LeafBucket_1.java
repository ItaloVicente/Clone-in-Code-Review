
package org.eclipse.jgit.notes;

abstract class InMemoryNoteBucket extends NoteBucket {
	final int prefixLen;

	InMemoryNoteBucket(int prefixLen) {
		this.prefixLen = prefixLen;
	}
}
