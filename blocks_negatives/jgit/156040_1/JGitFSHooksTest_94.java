        if (hookType.equals(FileSystemHooks.PostCommit)) {
            Assertions.assertThat(ctx.getParamValue(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE))
                    .isNotNull()
                    .isEqualTo(EXIT_CODE);
        }
    }
