
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;

class LocalCachedPack extends CachedPack {
	private final ObjectDirectory odb;

	private final String[] packNames;

	private PackFile[] packs;

	LocalCachedPack(ObjectDirectory odb
		this.odb = odb;
		this.packNames = packNames.toArray(new String[0]);
	}

	LocalCachedPack(List<PackFile> packs) {
		odb = null;
		packNames = null;
		this.packs = packs.toArray(new PackFile[0]);
	}

	@Override
	public long getObjectCount() throws IOException {
		long cnt = 0;
		for (PackFile pack : getPacks())
			cnt += pack.getObjectCount();
		return cnt;
	}

	void copyAsIs(PackOutputStream out
			throws IOException {
		for (PackFile pack : getPacks())
			pack.copyPackAsIs(out
	}

	@Override
	public boolean hasObject(ObjectToPack obj
		try {
			LocalObjectRepresentation local = (LocalObjectRepresentation) rep;
			for (PackFile pack : getPacks()) {
				if (local.pack == pack)
					return true;
			}
			return false;
		} catch (FileNotFoundException packGone) {
			return false;
		}
	}

	private PackFile[] getPacks() throws FileNotFoundException {
		if (packs == null) {
			PackFile[] p = new PackFile[packNames.length];
			for (int i = 0; i < packNames.length; i++)
				p[i] = getPackFile(packNames[i]);
			packs = p;
		}
		return packs;
	}

	private PackFile getPackFile(String packName) throws FileNotFoundException {
		for (PackFile pack : odb.getPacks()) {
			if (packName.equals(pack.getPackName()))
				return pack;
		}
		throw new FileNotFoundException(getPackFilePath(packName));
	}

	private String getPackFilePath(String packName) {
		final File packDir = odb.getPackDirectory();
		return new File(packDir
	}
}
