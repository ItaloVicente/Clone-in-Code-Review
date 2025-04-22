            boolean foundPreCommitHook = false;
            boolean foundPostCommitHook = false;
            for (File hook : hooks) {
                if (hook.getName().equals("pre-commit")) {
                    foundPreCommitHook = hook.canExecute();
                } else if (hook.getName().equals("post-commit")) {
                    foundPostCommitHook = hook.canExecute();
                }
            }
            assertThat(foundPreCommitHook).isTrue();
            assertThat(foundPostCommitHook).isTrue();
        }
    }
