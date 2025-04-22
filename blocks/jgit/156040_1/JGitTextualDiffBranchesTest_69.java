		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);

		diffs.forEach(diff -> {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
			assertThat(diff.getOldFilePath()).isEqualTo(diff.getNewFilePath());
			assertThat(diff.getLinesAdded()).isEqualTo(1);
			assertThat(diff.getLinesDeleted()).isEqualTo(1);
			assertThat(diff.getDiffText()).isNotEmpty();
		});

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(1));
		assertThat(diffs.get(1).getOldFilePath()).isEqualTo(TXT_FILES.get(2));
	}
