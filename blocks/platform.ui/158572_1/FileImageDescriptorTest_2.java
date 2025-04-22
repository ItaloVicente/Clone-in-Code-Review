				for (URL file : files) {
					startMeasuring();
					try {
						descriptor = ImageDescriptor.createFromFile(missing, FileLocator.toFileURL(file).getFile());
					} catch (IOException e) {
						fail(e.getLocalizedMessage(), e);
						continue;
					}
