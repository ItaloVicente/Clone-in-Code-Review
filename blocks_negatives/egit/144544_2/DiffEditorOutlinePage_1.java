				Folder folder = folders.get(path);
				if (folder == null) {
					folder = new Folder();
					folder.name = path;
					folder.files = new ArrayList<>();
					folders.put(path, folder);
				}
