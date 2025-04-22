		assertThat(diffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(0));
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(0);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(4);
		assertThat(diffs.get(0).getDiffText()).isNotEmpty();
	}
