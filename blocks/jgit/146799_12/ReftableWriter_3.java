		LogEntry entry = new LogEntry(ref
		if (lastLog != null && Entry.compare(lastLog
			illegalEntry(lastLog
		}
		lastLog = entry;
		logs.write(entry);
