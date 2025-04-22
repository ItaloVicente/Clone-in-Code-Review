				throw new SAXException(RepoText.get().invalidManifest);
			}
			currentProject = new RepoProject(
					attributes.getValue("name"), //$NON-NLS-1$
					attributes.getValue("path"), //$NON-NLS-1$
					attributes.getValue("revision"), //$NON-NLS-1$
					attributes.getValue("remote"), //$NON-NLS-1$
			currentProject.setRecommendShallow(
			Remote remote = new Remote(fetch, revision);
			remotes.put(attributes.getValue("name"), remote); //$NON-NLS-1$
			if (alias != null)
				remotes.put(alias, remote);
			if (currentProject == null)
				throw new SAXException(RepoText.get().invalidManifest);
			currentProject.addCopyFile(new CopyFile(
						rootRepo,
						currentProject.getPath(),
						attributes.getValue("src"), //$NON-NLS-1$
			if (currentProject == null) {
				throw new SAXException(RepoText.get().invalidManifest);
			}
			currentProject.addLinkFile(new LinkFile(
						rootRepo,
						currentProject.getPath(),
						attributes.getValue("src"), //$NON-NLS-1$
			if (includedReader != null) {
				try (InputStream is = includedReader.readIncludeFile(name)) {
					if (is == null) {
						throw new SAXException(
								RepoText.get().errorIncludeNotImplemented);
					}
					read(is);
				} catch (Exception e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile, name), e);
				}
			} else if (filename != null) {
				int index = filename.lastIndexOf('/');
				String path = filename.substring(0, index + 1) + name;
				try (InputStream is = new FileInputStream(path)) {
					read(is);
				} catch (IOException e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile, path), e);
				}
			}
			projects.removeIf((p) -> p.getName().equals(name));
		}
