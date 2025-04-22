					if(localImagePath.indexOf('.') < 0)
						continue;

					URL[] files = FileLocator.findEntries(bundle, new Path(
							localImagePath));

					for (URL file : files) {
						startMeasuring();
						try {
							descriptor = ImageDescriptor.createFromFile(missing, FileLocator.toFileURL(file).getFile());
						} catch (IOException e) {
							fail(e.getLocalizedMessage(), e);
							continue;
						}
