            @Override
            protected void setupFileSystemsManager() {
                fsManager = mock(JGitFileSystemsManager.class);
                when(fsManager.allTheFSAreClosed()).thenReturn(true);
            }
        });
