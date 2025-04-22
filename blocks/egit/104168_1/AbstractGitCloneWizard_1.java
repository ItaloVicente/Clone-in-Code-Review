					ProjectRecord record = new ProjectRecord(file);
					if (record.getProjectDescription() == null) {
						continue;
					}
					records.add(record);
				}
				if (records.isEmpty()) {
					return Status.OK_STATUS;
