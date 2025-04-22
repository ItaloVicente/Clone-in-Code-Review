				return;
			}
			IResource adapted = CommonUtils.getAdapterForObject(
					ssel.getFirstElement(), IResource.class);
			if (adapted != null) {
				showResource(adapted);
				return;
			}
			File file = CommonUtils.getAdapterForObject(ssel.getFirstElement(),
					File.class);
			if (file != null) {
				IPath path = new Path(file.getAbsolutePath());
				showPaths(Arrays.asList(path));
				return;
