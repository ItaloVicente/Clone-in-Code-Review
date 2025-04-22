        List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

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

    @Test
    public void testDiffWithUpdateFirstAndLastLines() throws IOException {
        commit(git, DEVELOP_BRANCH, "Updating file",
               content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1Changed", "Line2", "Line3", "Line4Changed")));

        List<TextualDiff> fileDiffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

        assertThat(fileDiffs).isNotEmpty();
        assertThat(fileDiffs).hasSize(1);
    }

    @Test
    public void testDiffWithEvenBranches() {
        List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);
