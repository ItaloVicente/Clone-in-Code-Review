		PrintWriter writer = new PrintWriter(out);
		writer.println(NLS.bind(WorkbenchMessages.SystemSummary_timeStamp,
				DateFormat
						.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL)
						.format(new Date())));

		ConfigurationInfo.appendExtensions(writer);
		writer.close();
