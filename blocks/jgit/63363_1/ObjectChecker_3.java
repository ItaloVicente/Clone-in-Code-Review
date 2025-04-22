
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectIdSet;

public class LazyObjectIdSetFile implements ObjectIdSet {
	private final File src;
	private ObjectIdOwnerMap<Entry> set;

	public LazyObjectIdSetFile(File src) {
		this.src = src;
	}

	@Override
	public boolean contains(AnyObjectId objectId) {
		if (set == null) {
			set = load();
		}
		return set.contains(objectId);
	}

	private ObjectIdOwnerMap<Entry> load() {
		ObjectIdOwnerMap<Entry> r = new ObjectIdOwnerMap<>();
		try (FileInputStream fin = new FileInputStream(src);
				Reader rin = new InputStreamReader(fin
				BufferedReader br = new BufferedReader(rin)) {
			MutableObjectId id = new MutableObjectId();
			for (String line; (line = br.readLine()) != null;) {
				id.fromString(line);
				if (!r.contains(id)) {
					r.add(new Entry(id));
				}
			}
		} catch (IOException e) {
		}
		return r;
	}

	static class Entry extends ObjectIdOwnerMap.Entry {
		Entry(AnyObjectId id) {
			super(id);
		}
	}
}
