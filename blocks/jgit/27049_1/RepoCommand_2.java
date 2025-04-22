			if (reverse) {
				reverseProjects.add(name);
				reverseDeletes.add(name);
				for (CopyFile f : copyfiles)
					reverseDeletes.add(f.dest);
			} else {
				Project proj = new Project(url
				proj.copyfiles.addAll(copyfiles);
				bareProjects.add(proj);
			}
