			try {
				IProject project = m.getContainer().getProject();
				FileRepository repo = new FileRepository(m.getGitDirAbsolutePath().toFile());
				File workTree = repo.getWorkTree();
				IPath workTreePath = Path.fromOSString(workTree.getAbsolutePath());
				if (workTreePath.isPrefixOf(project.getLocation())) {
					IPath makeRelativeTo = project.getLocation().makeRelativeTo(workTreePath);
					String repoRelativePath = makeRelativeTo.append("/.project").toPortableString(); //$NON-NLS-1$
					ObjectId headCommitId = repo.resolve(Constants.HEAD);
					if (headCommitId != null) {
						RevWalk revWalk = new RevWalk(repo);
						RevCommit headCommit = revWalk.parseCommit(headCommitId);
						RevTree headTree = headCommit.getTree();
						TreeWalk projectInRepo = TreeWalk.forPath(repo, repoRelativePath, headTree);
						if (projectInRepo != null) {
							treeItem2.setChecked(true);
						}
						revWalk.dispose();
					}
				}
				repo.close();
			} catch (IOException e1) {
				Activator.logError("Failed to detect which repository to use", e1); //$NON-NLS-1$
			}
