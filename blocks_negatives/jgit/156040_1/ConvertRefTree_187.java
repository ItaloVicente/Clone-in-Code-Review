    private final Git git;
    private String txnNamespace;
    private String txnCommitted;

    public ConvertRefTree(final Git git) {
        this.git = git;
    }

    public void execute() throws IOException {
        try (ObjectReader reader = git.getRepository().newObjectReader();
             RevWalk rw = new RevWalk(reader);
             ObjectInserter inserter = git.getRepository().newObjectInserter()) {
            RefDatabase refDb = git.getRepository().getRefDatabase();
            if (refDb instanceof RefTreeDatabase) {
                RefTreeDatabase d = (RefTreeDatabase) refDb;
                refDb = d.getBootstrap();
                txnNamespace = d.getTxnNamespace();
                txnCommitted = d.getTxnCommitted();
            } else {
                RefTreeDatabase d = new RefTreeDatabase(git.getRepository(),
                                                        refDb);
                txnNamespace = d.getTxnNamespace();
                txnCommitted = d.getTxnCommitted();
            }

            CommitBuilder b = new CommitBuilder();
            Ref ref = refDb.exactRef(txnCommitted);
            RefUpdate update = refDb.newUpdate(txnCommitted,
                                               true);
            ObjectId oldTreeId;

            if (ref != null && ref.getObjectId() != null) {
                ObjectId oldId = ref.getObjectId();
                update.setExpectedOldObjectId(oldId);
                b.setParentId(oldId);
                oldTreeId = rw.parseCommit(oldId).getTree();
            } else {
                update.setExpectedOldObjectId(ObjectId.zeroId());
                oldTreeId = ObjectId.zeroId();
            }

            RefTree tree = rebuild(refDb);
            b.setTreeId(tree.writeTree(inserter));
            b.setAuthor(new PersonIdent("system",
                                        "system",
                                        new Date(1481754897254L),
                                        TimeZone.getDefault()));
            b.setCommitter(b.getAuthor());
            if (b.getTreeId().equals(oldTreeId)) {
                return;
            }

            update.setNewObjectId(inserter.insert(b));
            inserter.flush();

            RefUpdate.Result result = update.update(rw);
            switch (result) {
                case NEW:
                case FAST_FORWARD:
                    break;
                default:
                    throw new RuntimeException(String.format("%s: %s",
                                                             update.getName(),
            }

            if (!(git.getRepository().getRefDatabase() instanceof RefTreeDatabase)) {
                StoredConfig cfg = git.getRepository().getConfig();
                cfg.setInt("core",
                           null,
                           "repositoryformatversion",
                cfg.setString("extensions",
                              null,
                              "refsStorage",
                cfg.save();

                final Repository repo = new FileRepositoryBuilder()
                        .setGitDir(git.getRepository().getDirectory())
                        .build();
                git.updateRepo(repo);
            }
            final File commited = new File(git.getRepository().getDirectory(),
                                           txnCommitted);
            final File accepted = new File(git.getRepository().getDirectory(),
                                           txnNamespace + "accepted");
            Files.copy(commited.toPath(),
                       accepted.toPath(),
                       StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private RefTree rebuild(RefDatabase refdb) throws IOException {
        RefTree tree = RefTree.newEmptyTree();
        List<Command> cmds
                = new ArrayList<>();

        Ref head = refdb.exactRef(HEAD);
        if (head != null) {
            cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(
                    null,
                    head));
        }

        for (Ref r : refdb.getRefsByPrefix(RefDatabase.ALL)) {
            if (r.getName().equals(txnCommitted) || r.getName().equals(HEAD)
                    || r.getName().startsWith(txnNamespace)) {
                continue;
            }
            cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(
                    null,
                    git.getRepository().getRefDatabase().peel(r)));
        }
        tree.apply(cmds);
        return tree;
    }
