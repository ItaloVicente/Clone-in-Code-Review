			if (maxNumberOfFiles > 0 && r.size() > maxNumberOfFiles) {
				while (r.size() > maxNumberOfFiles)
					r.remove(r.size() - 1);

				r.add(new FileDiff.MoreAvailable(commit, maxNumberOfFiles));
			}
		} else { // DiffEntry does not support walks with more than two trees
