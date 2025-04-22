package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GraftsDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

class FileGraftsDataBase implements GraftsDatabase {
	private final File graftsFile;
	private FileSnapshot snapShot;

	private Map<AnyObjectId

	FileGraftsDataBase(File graftsFile) {
		this.graftsFile = graftsFile;
	}

	boolean exists() {
		return graftsFile.exists();
	}

	boolean isOutdated() {
		if (snapShot == null && graftsFile.exists())
			return true;
		if (snapShot != null && !graftsFile.exists()) {
			snapShot = null;
			return true;
		}
		return snapShot == null || snapShot.isModified(graftsFile);
	}

	public Map<AnyObjectId
		if (isOutdated())
			grafts = loadGrafts();
		return grafts;
	}

	Map<AnyObjectId
		throws IOException {
		try {
			if (!graftsFile.exists()) {
				snapShot = null;
				return null;
			}
			HashMap<AnyObjectId
			snapShot = FileSnapshot.save(graftsFile);
			byte[] graftData = IO.readFully(graftsFile);
			int p = 0;
			int line = 1;
			while (p < graftData.length) {
				ObjectId child = ObjectId.fromString(graftData
				int endOfLine = RawParseUtils.nextLF(graftData
				int parentCount = (endOfLine - p)
						/ Constants.OBJECT_ID_STRING_LENGTH - 1;
				p += Constants.OBJECT_ID_STRING_LENGTH;
				List<AnyObjectId> parents;
				if (parentCount == 0)
					parents = Collections.<AnyObjectId> emptyList();
				else if (parentCount == 1) {
					if (graftData[p] != ' ')
						throw new IOException("Invlid graft file at line "
								+ line + " in " + graftsFile);
					AnyObjectId parent = ObjectId.fromString(graftData
					p += 1 + Constants.OBJECT_ID_STRING_LENGTH;
					if (graftData[p] != '\n')
						throw new IOException("Invlid graft file at line "
								+ line + " in " + graftsFile);
					parents = Collections.<AnyObjectId> singletonList(parent);
				} else {
					parents = new ArrayList<AnyObjectId>(parentCount);
					while (p < endOfLine - 1) {
						if (graftData[p] != ' ')
							throw new IOException(
									"Invlid graft file at line " + line
											+ " in " + graftsFile);
						AnyObjectId parent = ObjectId.fromString(graftData
								p + 1);
						p += 1 + Constants.OBJECT_ID_STRING_LENGTH;
						parents.add(parent);
					}
					if (graftData[p] != '\n')
						throw new IOException("Invlid graft file at line "
								+ line + " in " + graftsFile);
				}
				ret.put(child
				line++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
