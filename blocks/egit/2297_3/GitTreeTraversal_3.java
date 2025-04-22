					resource = ROOT.getFileForLocation(childPath);
				else if (objectType == Constants.OBJ_TREE) {
					resource = ROOT.getProject(name);
					if (resource == null)
						resource = ROOT.getFolder(childPath.makeRelativeTo(ROOT.getLocation()));

					resource = ROOT.getContainerForLocation(childPath);
				}
