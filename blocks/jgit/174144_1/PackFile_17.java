
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PackDirectory {
	private final static Logger LOG = LoggerFactory
			.getLogger(PackDirectory.class);

	private static final PackList NO_PACKS = new PackList(FileSnapshot.DIRTY
			new PackFile[0]);

	private final Config config;

	private final File directory;

	private final AtomicReference<PackList> packList;

	PackDirectory(Config config
		this.config = config;
		this.directory = directory;
		packList = new AtomicReference<>(NO_PACKS);
	}

	File getDirectory() {
		return directory;
	}

	void create() throws IOException {
		FileUtils.mkdir(directory);
	}

	void close() {
		PackList packs = packList.get();
		if (packs != NO_PACKS && packList.compareAndSet(packs
			for (PackFile p : packs.packs) {
				p.close();
			}
		}
	}

	Collection<PackFile> getPacks() {
		PackList list = packList.get();
		if (list == NO_PACKS) {
			list = scanPacks(list);
		}
		PackFile[] packs = list.packs;
		return Collections.unmodifiableCollection(Arrays.asList(packs));
	}

	@Override
	public String toString() {
	}

	boolean has(AnyObjectId objectId) {
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					if (p.hasObject(objectId)) {
						return true;
					}
				} catch (IOException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().unableToReadPackfile
							p.getPackFile().getAbsolutePath())
					remove(p);
				}
			}
		} while (searchPacksAgain(pList));
		return false;
	}

	boolean resolve(Set<ObjectId> matches
			int matchLimit) {
		int oldSize = matches.size();
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					p.resolve(matches
					p.resetTransientErrorCount();
				} catch (IOException e) {
					handlePackError(e
				}
				if (matches.size() > matchLimit) {
					return false;
				}
			}
		} while (matches.size() == oldSize && searchPacksAgain(pList));
		return true;
	}

	ObjectLoader open(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						ObjectLoader ldr = p.get(curs
						p.resetTransientErrorCount();
						if (ldr != null)
							return ldr;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList)) {
							continue SEARCH;
						}
					} catch (IOException e) {
						handlePackError(e
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return null;
	}

	long getSize(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs
						p.resetTransientErrorCount();
						if (0 <= len) {
							return len;
						}
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList)) {
							continue SEARCH;
						}
					} catch (IOException e) {
						handlePackError(e
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return -1;
	}

	void selectRepresentation(PackWriter packer
			WindowCursor curs) {
		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (PackFile p : pList.packs) {
				try {
					LocalObjectRepresentation rep = p.representation(curs
					p.resetTransientErrorCount();
					if (rep != null) {
						packer.select(otp
					}
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					handlePackError(e
				}
			}
			break SEARCH;
		}
	}

	private void handlePackError(IOException e
		String warnTmpl = null;
		int transientErrorCount = 0;
		String errTmpl = JGitText.get().exceptionWhileReadingPack;
		if ((e instanceof CorruptObjectException)
				|| (e instanceof PackInvalidException)) {
			warnTmpl = JGitText.get().corruptPack;
			LOG.warn(MessageFormat.format(warnTmpl
					p.getPackFile().getAbsolutePath())
			remove(p);
		} else if (e instanceof FileNotFoundException) {
			if (p.getPackFile().exists()) {
				errTmpl = JGitText.get().packInaccessible;
				transientErrorCount = p.incrementTransientErrorCount();
			} else {
				warnTmpl = JGitText.get().packWasDeleted;
				remove(p);
			}
		} else if (FileUtils.isStaleFileHandleInCausalChain(e)) {
			warnTmpl = JGitText.get().packHandleIsStale;
			remove(p);
		} else {
			transientErrorCount = p.incrementTransientErrorCount();
		}
		if (warnTmpl != null) {
			LOG.warn(MessageFormat.format(warnTmpl
					p.getPackFile().getAbsolutePath())
		} else {
			if (doLogExponentialBackoff(transientErrorCount)) {
				LOG.error(MessageFormat.format(errTmpl
						p.getPackFile().getAbsolutePath()
						Integer.valueOf(transientErrorCount))
			}
		}
	}

	private boolean doLogExponentialBackoff(int n) {
		return (n & (n - 1)) == 0;
	}

	boolean searchPacksAgain(PackList old) {
		boolean trustFolderStat = config.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT

		return ((!trustFolderStat) || old.snapshot.isModified(directory))
				&& old != scanPacks(old);
	}

	void insert(PackFile pf) {
		PackList o
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final String name = pf.getPackFile().getName();
			for (PackFile p : oldList) {
				if (name.equals(p.getPackFile().getName())) {
					return;
				}
			}

			final PackFile[] newList = new PackFile[1 + oldList.length];
			newList[0] = pf;
			System.arraycopy(oldList
			n = new PackList(o.snapshot
		} while (!packList.compareAndSet(o
	}

	private void remove(PackFile deadPack) {
		PackList o
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final int j = indexOf(oldList
			if (j < 0) {
				break;
			}

			final PackFile[] newList = new PackFile[oldList.length - 1];
			System.arraycopy(oldList
			System.arraycopy(oldList
			n = new PackList(o.snapshot
		} while (!packList.compareAndSet(o
		deadPack.close();
	}

	private static int indexOf(PackFile[] list
		for (int i = 0; i < list.length; i++) {
			if (list[i] == pack) {
				return i;
			}
		}
		return -1;
	}

	private PackList scanPacks(PackList original) {
		synchronized (packList) {
			PackList o
			do {
				o = packList.get();
				if (o != original) {
					return o;
				}
				n = scanPacksImpl(o);
				if (n == o) {
					return n;
				}
			} while (!packList.compareAndSet(o
			return n;
		}
	}

	private PackList scanPacksImpl(PackList old) {
		final Map<String
		final FileSnapshot snapshot = FileSnapshot.save(directory);
		final Set<String> names = listPackDirectory();
		final List<PackFile> list = new ArrayList<>(names.size() >> 2);
		boolean foundNew = false;
		for (String indexName : names) {
				continue;
			}

			final String base = indexName.substring(0
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension())) {
					extensions |= ext.getBit();
				}
			}

			if ((extensions & PACK.getBit()) == 0) {
				continue;
			}

			final String packName = base + PACK.getExtension();
			final File packFile = new File(directory
			final PackFile oldPack = forReuse.get(packName);
			if (oldPack != null
					&& !oldPack.getFileSnapshot().isModified(packFile)) {
				forReuse.remove(packName);
				list.add(oldPack);
				continue;
			}

			list.add(new PackFile(packFile
			foundNew = true;
		}

		if (!foundNew && forReuse.isEmpty() && snapshot.equals(old.snapshot)) {
			old.snapshot.setClean(snapshot);
			return old;
		}

		for (PackFile p : forReuse.values()) {
			p.close();
		}

		if (list.isEmpty()) {
			return new PackList(snapshot
		}

		final PackFile[] r = list.toArray(new PackFile[0]);
		Arrays.sort(r
		return new PackList(snapshot
	}

	private static Map<String
		final Map<String
		for (PackFile p : old.packs) {
			if (p.invalid()) {
				p.close();
				continue;
			}

			final PackFile prior = forReuse.put(p.getPackFile().getName()
			if (prior != null) {
				forReuse.put(prior.getPackFile().getName()
				p.close();
			}
		}
		return forReuse;
	}

	private Set<String> listPackDirectory() {
		final String[] nameList = directory.list();
		if (nameList == null) {
			return Collections.emptySet();
		}
		final Set<String> nameSet = new HashSet<>(nameList.length << 1);
		for (String name : nameList) {
				nameSet.add(name);
			}
		}
		return nameSet;
	}

	static final class PackList {
		final FileSnapshot snapshot;

		final PackFile[] packs;

		PackList(FileSnapshot monitor
			this.snapshot = monitor;
			this.packs = packs;
		}
	}
}
