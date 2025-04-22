			try {
				if (dc != null) {
					dc.unlock();
				}
			} finally {
				if (!actuallyDeletedFiles.isEmpty()) {
					repo.fireEvent(new WorkingTreeModifiedEvent(null
							actuallyDeletedFiles));
				}
			}
