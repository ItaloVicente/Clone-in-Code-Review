		IResource[] roots = grvt.roots();
		Arrays.sort(roots, new Comparator<IResource>() {
			public int compare(IResource r1, IResource r2) {
				String path1 = r1.getFullPath().toString();
				String path2 = r2.getFullPath().toString();
				return path1.compareTo(path2);
			}
		});
		assertEquals(2, roots.length);
		IResource actualProject = roots[0];
		assertEquals(this.project.project, actualProject);
		IResource actualProject1 = roots[1];
		assertEquals(secondIProject, actualProject1);
