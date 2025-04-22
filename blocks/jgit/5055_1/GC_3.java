				File idx = nameFor(repo
				if (!pack.createNewFile()) {
					for (PackFile f : repo.getObjectDatabase().getPacks())
						if (f.getPackName().equals(id))
							return (f);
					throw new IOException(
							MessageFormat.format(
									JGitText.get().cannotCreatePackfile
									pack.getPath()));
				}
				if (!idx.createNewFile())
					throw new IOException(
							MessageFormat.format(
									JGitText.get().cannotCreateIndexfile
									idx.getPath()));
