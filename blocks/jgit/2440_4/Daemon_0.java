		final FileResolver<DaemonClient> resolver = new FileResolver<DaemonClient>();
		for (final File f : directory) {
			out.println(MessageFormat.format(CLIText.get().exporting
			resolver.exportDirectory(f);
		}
		resolver.setExportAll(exportAll);
