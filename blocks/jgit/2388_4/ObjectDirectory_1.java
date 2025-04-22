
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;

class LocalCachedPack extends CachedPack {
	private final ObjectDirectory odb;

	private final Set<ObjectId> tips;

	private final String[] packNames;

	LocalCachedPack(ObjectDirectory odb
			List<String> packNames) {
		this.odb = odb;

		if (tips.size() == 1)
			this.tips = Collections.singleton(tips.iterator().next());
		else
			this.tips = Collections.unmodifiableSet(tips);

		this.packNames = packNames.toArray(new String[packNames.size()]);
	}

	@Override
	public Set<ObjectId> getTips() {
		return tips;
	}

	@Override
	public long getObjectCount() throws IOException {
		long cnt = 0;
		for (String packName : packNames)
			cnt += getPackFile(packName).getObjectCount();
		return cnt;
	}

	void copyAsIs(PackOutputStream out
		for (String packName : packNames)
			getPackFile(packName).copyPackAsIs(out
	}

	@Override
	public <T extends ObjectId> Set<ObjectId> hasObject(Iterable<T> toFind)
			throws IOException {
		PackFile[] packs = new PackFile[packNames.length];
		for (int i = 0; i < packNames.length; i++)
			packs[i] = getPackFile(packNames[i]);

		Set<ObjectId> have = new HashSet<ObjectId>();
		for (ObjectId id : toFind) {
			for (PackFile pack : packs) {
				if (pack.hasObject(id)) {
					have.add(id);
					break;
				}
			}
		}
		return have;
	}

	private PackFile getPackFile(String packName) throws FileNotFoundException {
		for (PackFile pack : odb.getPacks()) {
			if (packName.equals(pack.getPackName()))
				return pack;
		}
		throw new FileNotFoundException(getPackFilePath(packName));
	}

	private String getPackFilePath(String packName) {
		final File packDir = new File(odb.getDirectory()
		return new File(packDir
	}
}
