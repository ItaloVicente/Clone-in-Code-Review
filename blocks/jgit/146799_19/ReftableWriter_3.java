		LogEntry entry = new LogEntry(ref
		if (lastLog != null && Entry.compare(lastLog
			throwIllegalEntry(lastLog
		}
		lastLog = entry;
		logs.write(entry);
