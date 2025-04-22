				command.addSubmodule(remoteUrl + proj.name
						proj.path
						proj.revision == null
								? defaultRevision : proj.revision
						proj.copyfiles);
			}
		}

		void removeNotInGroup() {
			Iterator<Project> iter = projects.iterator();
			while (iter.hasNext())
				if (!inGroups(iter.next()))
					iter.remove();
		}

		void removeOverlaps() {
			Collections.sort(projects);
			Iterator<Project> iter = projects.iterator();
			if (iter.hasNext()) {
				Project last = iter.next();
				while (iter.hasNext()) {
					Project p = iter.next();
					if (last.isParentTo(p))
						iter.remove();
					else
						last = p;
