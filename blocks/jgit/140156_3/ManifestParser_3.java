		if (null != qName) switch (qName) {
                        throw new SAXException(RepoText.get().invalidManifest);
                    }
                    currentProject = new RepoProject(
                            attributes.getValue("name")
                            attributes.getValue("path")
                            attributes.getValue("revision")
                            attributes.getValue("remote")
                    currentProject.setRecommendShallow(
                    break;
                    Remote remote = new Remote(fetch
                    remotes.put(attributes.getValue("name")
                    if (alias != null)
                        remotes.put(alias
                    break;
                    break;
                    if (currentProject == null)
                        throw new SAXException(RepoText.get().invalidManifest);
                    currentProject.addCopyFile(new CopyFile(
                            rootRepo
                            currentProject.getPath()
                            attributes.getValue("src")
                    break;
                    if (currentProject == null) {
                        throw new SAXException(RepoText.get().invalidManifest);
                    }
                    currentProject.addLinkFile(new LinkFile(
                            rootRepo
                            currentProject.getPath()
                            attributes.getValue("src")
                    break;
                    if (includedReader != null) {
                        try (InputStream is = includedReader.readIncludeFile(name)) {
                            if (is == null) {
                                throw new SAXException(
                                        RepoText.get().errorIncludeNotImplemented);
                            }
                            read(is);
                        } catch (Exception e) {
                            throw new SAXException(MessageFormat.format(
                                    RepoText.get().errorIncludeFile
                        }
                    } else if (filename != null) {
                        int index = filename.lastIndexOf('/');
                        String path = filename.substring(0
                        try (InputStream is = new FileInputStream(path)) {
                            read(is);
                        } catch (IOException e) {
                            throw new SAXException(MessageFormat.format(
                                    RepoText.get().errorIncludeFile
                        }
                    }
                        break;
                    }
                    projects.removeIf((p) -> p.getName().equals(name));
                        break;
                    }
                default:
                    break;
            }
