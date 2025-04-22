			service.exportPreferences(node, stm, null);
		} catch (CoreException e) {
		}

		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_userPreferences);

		BufferedReader reader = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(stm.toByteArray());
			reader = new BufferedReader(new InputStreamReader(in, "8859_1")); //$NON-NLS-1$
			char[] chars = new char[8192];

			while (true) {
				int read = reader.read(chars);
				if (read <= 0) {
					break;
				}
				writer.write(chars, 0, read);
