				return;
			}
			IResource adapted = AdapterUtils.adapt(ssel.getFirstElement(),
					IResource.class);
			if (adapted != null) {
				showResource(adapted);
				return;
			}
			File file = AdapterUtils.adapt(ssel.getFirstElement(), File.class);
			if (file != null) {
				IPath path = new Path(file.getAbsolutePath());
				showPaths(Arrays.asList(path));
				return;
