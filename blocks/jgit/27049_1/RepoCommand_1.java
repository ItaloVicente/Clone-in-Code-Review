			Config cfg;
			ObjectId oid;
			try {
				oid = repo.resolve(
				if (oid == null)
					cfg = new Config();
				else
					cfg = new BlobBasedConfig(null
			} catch (IOException e) {
				throw new ManifestErrorException(e);
			} catch (ConfigInvalidException e) {
				throw new ManifestErrorException(e);
			}

			if (oldPath != null) {
				reverseProjects = new ArrayList<String>();
				reverseDeletes = new ArrayList<String>();
				manifest = new XmlManifest(this
				manifest.setReverse(true);
				try {
					manifest.read();
				} catch (IOException e) {
					throw new ManifestErrorException(e);
				}

				for (String name : reverseProjects)
					cfg.unsetSection("submodule"

				DirCacheEditor editor = index.editor();
				for (String path : reverseDeletes) {
					DeletePath delete = new DeletePath(path);
					editor.add(delete);
				}
				editor.finish();
			}

