
package org.eclipse.jgit.storage.pack;

class DeltaWindowEntry {
	ObjectToPack object;

	byte[] buffer;

	DeltaIndex index;

	void set(ObjectToPack object) {
		this.object = object;
		this.index = null;
		this.buffer = null;
	}

	int depth() {
		return object.getDeltaDepth();
	}

	int type() {
		return object.getType();
	}

	int size() {
		return object.getWeight();
	}

	boolean empty() {
		return object == null;
	}
}
