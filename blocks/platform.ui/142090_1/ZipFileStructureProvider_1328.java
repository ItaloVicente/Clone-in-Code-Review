			return zipFile.getInputStream((ZipEntry) element);
		} catch (IOException e) {
			IDEWorkbenchPlugin.log(e.getLocalizedMessage(), e);
			return null;
		}
	}

	@Override
