			Arrays.sort(roots, new Comparator<IResource>() {
				@Override
				public int compare(IResource r1, IResource r2) {
					String path1 = r1.getFullPath().toString();
					String path2 = r2.getFullPath().toString();
					return path1.compareTo(path2);
				}
			});
