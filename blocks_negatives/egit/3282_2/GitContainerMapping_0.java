				IFile file = ROOT.getFileForLocation(location);
				if (file == null)
					continue;

				traversal = new ResourceTraversal(new IResource[] { file },
						DEPTH_ONE, ALLOW_MISSING_LOCAL);
