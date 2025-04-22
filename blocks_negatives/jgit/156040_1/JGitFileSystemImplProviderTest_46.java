    }

    private VersionRecord makeVersionRecord(final String author,
                                            final String email,
                                            final String comment,
                                            final Date date,
                                            final String commit) {
        return new VersionRecord() {
            @Override
            public String id() {
                return commit;
            }

            @Override
            public String author() {
                return author;
            }

            @Override
            public String email() {
                return email;
            }

            @Override
            public String comment() {
                return comment;
            }

            @Override
            public Date date() {
                return date;
            }

            @Override
            public String uri() {
                return null;
            }
        };
    }

    private List<RevCommit> getCommitsFromBranch(final GitImpl origin,
                                                 String branch) throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
        List<RevCommit> commits = new ArrayList<>();
        final ObjectId id = new GetRef(origin.getRepository(),
                                       branch).execute().getObjectId();
        for (RevCommit commit : origin._log().add(id).call()) {
            commits.add(commit);
        }
        return commits;
    }
