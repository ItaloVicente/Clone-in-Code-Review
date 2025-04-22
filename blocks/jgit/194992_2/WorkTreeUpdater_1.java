                try (LfsInputStream is =
                    org.eclipse.jgit.util.LfsFactory.getInstance()
                    .applyCleanFilter(
                        repo
                        resultStreamLoader.data.load()
                        resultStreamLoader.size
                        lfsAttribute)) {
                  return inserter.insert(OBJ_BLOB
                }
