				Folder folder = folders.computeIfAbsent(path, key -> {
					Folder newFolder = new Folder();
					newFolder.name = key;
					newFolder.files = new ArrayList<>();
					return newFolder;
				});
