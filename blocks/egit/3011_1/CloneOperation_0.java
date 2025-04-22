			CloneCommand cloneRepository = Git.cloneRepository();
			cloneRepository.setCredentialsProvider(credentialsProvider);
			cloneRepository.setBranch(refName);
			cloneRepository.setDirectory(workdir);
			cloneRepository.setProgressMonitor(gitMonitor);
			cloneRepository.setRemote(remoteName);
			cloneRepository.setURI(uri.toString());
			cloneRepository.setTimeout(timeout);
			cloneRepository.setCloneAllBranches(allSelected);
			if (selectedBranches != null) {
				List<String> branches = new ArrayList<String>();
				for (Ref branch : selectedBranches)
					branches.add(branch.getName());
				cloneRepository.setBranchesToClone(branches);
