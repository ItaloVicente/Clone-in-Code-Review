            writeMockHook(hooksDir,
                          "post-commit");
            writeMockHook(hooksDir,
                          PreCommitHook.NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
