	private void insertPack(PackFile pf) {
		PackList o, n;
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final String name = pf.getPackFile().getName();
			for (PackFile p : oldList) {
				if (name.equals(p.getPackFile().getName()))
					return;
			}

			final PackFile[] newList = new PackFile[1 + oldList.length];
			newList[0] = pf;
			System.arraycopy(oldList, 0, newList, 1, oldList.length);
			n = new PackList(o.snapshot, newList);
		} while (!packList.compareAndSet(o, n));
	}

	private void removePack(PackFile deadPack) {
		PackList o, n;
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final int j = indexOf(oldList, deadPack);
			if (j < 0)
				break;

			final PackFile[] newList = new PackFile[oldList.length - 1];
			System.arraycopy(oldList, 0, newList, 0, j);
			System.arraycopy(oldList, j + 1, newList, j, newList.length - j);
			n = new PackList(o.snapshot, newList);
		} while (!packList.compareAndSet(o, n));
		deadPack.close();
	}

	private static int indexOf(PackFile[] list, PackFile pack) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == pack)
				return i;
		}
		return -1;
	}

	private PackList scanPacks(PackList original) {
		synchronized (packList) {
			PackList o, n;
			do {
				o = packList.get();
				if (o != original) {
					return o;
				}
				n = scanPacksImpl(o);
				if (n == o)
					return n;
			} while (!packList.compareAndSet(o, n));
			return n;
		}
	}

	private PackList scanPacksImpl(PackList old) {
		final Map<String, PackFile> forReuse = reuseMap(old);
		final FileSnapshot snapshot = FileSnapshot.save(packDirectory);
		final Set<String> names = listPackDirectory();
		final List<PackFile> list = new ArrayList<>(names.size() >> 2);
		boolean foundNew = false;
		for (String indexName : names) {
				continue;

			final String base = indexName.substring(0, indexName.length() - 3);
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension()))
					extensions |= ext.getBit();
			}

			if ((extensions & PACK.getBit()) == 0) {
				continue;
			}

			final String packName = base + PACK.getExtension();
			final PackFile oldPack = forReuse.remove(packName);
			if (oldPack != null) {
				list.add(oldPack);
				continue;
			}

			final File packFile = new File(packDirectory, packName);
			list.add(new PackFile(packFile, extensions));
			foundNew = true;
		}

		if (!foundNew && forReuse.isEmpty() && snapshot.equals(old.snapshot)) {
			old.snapshot.setClean(snapshot);
			return old;
		}

		for (PackFile p : forReuse.values()) {
			p.close();
		}

		if (list.isEmpty())
			return new PackList(snapshot, NO_PACKS.packs);

		final PackFile[] r = list.toArray(new PackFile[list.size()]);
		Arrays.sort(r, PackFile.SORT);
		return new PackList(snapshot, r);
	}

	private static Map<String, PackFile> reuseMap(PackList old) {
		final Map<String, PackFile> forReuse = new HashMap<>();
		for (PackFile p : old.packs) {
			if (p.invalid()) {
				p.close();
				continue;
			}

			final PackFile prior = forReuse.put(p.getPackFile().getName(), p);
			if (prior != null) {
				forReuse.put(prior.getPackFile().getName(), prior);
				p.close();
			}
		}
		return forReuse;
	}

	private Set<String> listPackDirectory() {
		final String[] nameList = packDirectory.list();
		if (nameList == null)
			return Collections.emptySet();
		final Set<String> nameSet = new HashSet<>(nameList.length << 1);
		for (String name : nameList) {
				nameSet.add(name);
		}
		return nameSet;
	}

