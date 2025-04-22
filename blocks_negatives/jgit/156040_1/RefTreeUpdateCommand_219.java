    private final Git git;
    private final String name;
    private final RevCommit commit;

    public RefTreeUpdateCommand(final Git git,
                                final String branchName,
                                final RevCommit commit) {
        this.git = git;
        this.name = branchName;
        this.commit = commit;
    }

    public void execute() throws java.io.IOException, ConcurrentRefUpdateException {
        update(git.getRepository(),
               Constants.R_HEADS + name,
               commit);
        if (name.equals(MASTER) && !git.isHEADInitialized()) {
            synchronized (git.getRepository()) {
                symRef(git,
                       HEAD,
                       Constants.R_HEADS + name);
                git.setHeadAsInitialized();
            }
        }
    }

    private void symRef(final Git git,
                        final String name,
                        final String dst)
            throws java.io.IOException {
        commit(git.getRepository(),
               null,
               (reader, tree) -> {
                   Ref old = tree.exactRef(reader,
                                           name);
                   Ref newx = tree.exactRef(reader,
                                            dst);
                   final Command n;
                   if (newx != null) {
                       n = new Command(old,
                                       new SymbolicRef(name,
                                                       newx));
                   } else {
                       n = new Command(old,
                                       new SymbolicRef(name,
                                                       new ObjectIdRef.Unpeeled(Ref.Storage.NEW,
                                                                                dst,
                                                                                null)));
                   }
                   return tree.apply(Collections.singleton(n));
               });
    }

    private void update(final Repository _repo,
                        final String _name,
                        final RevCommit _commit)
            throws IOException {
        commit(_repo,
               _commit,
               (reader, refTree) -> {
                   final Ref old = refTree.exactRef(reader,
                                                    _name);
                   final List<Command> n = new ArrayList<>(1);
                   try (RevWalk rw = new RevWalk(_repo)) {
                       n.add(new Command(old,
                                         toRef(rw,
                                               _commit,
                                               _name,
                                               true)));
                       if (git.isKetchEnabled()) {
                           proposeKetch(n,
                                        _commit);
                       }
                   } catch (final IOException | InterruptedException e) {
                       String msg = JGitText.get().transactionAborted;
                       for (Command cmd : n) {
                           if (cmd.getResult() == NOT_ATTEMPTED) {
                               cmd.setResult(REJECTED_OTHER_REASON,
                                             msg);
                           }
                       }
                       throw new GitException("Error");
                   }
                   return refTree.apply(n);
               });
    }

    private void proposeKetch(final List<Command> n,
                              final RevCommit _commit) throws IOException, InterruptedException {
        final Proposal proposal = new Proposal(n)
                .setAuthor(_commit.getAuthorIdent())
                .setMessage("push");
        git.getKetchLeader().queueProposal(proposal);
        if (proposal.isDone()) {
            throw new GitException("Error");
        }
        if (proposal.getState() == QUEUED) {
            waitForQueue(proposal);
        }
        if (!proposal.isDone()) {
            waitForPropose(proposal);
        }
    }

    private void waitForQueue(final Proposal proposal)
            throws InterruptedException {
        while (!proposal.awaitStateChange(QUEUED,
                                          250,
                                          MILLISECONDS)) {
            System.out.println("waiting queue...");
        }
        switch (proposal.getState()) {
            case RUNNING:
            default:
                break;

            case EXECUTED:
                break;

            case ABORTED:
                break;
        }
    }

    private void waitForPropose(final Proposal proposal) throws InterruptedException {
        while (!proposal.await(250,
                               MILLISECONDS)) {
            System.out.println("waiting propose...");
        }
    }

    private static Ref toRef(final RevWalk rw,
                             final ObjectId id,
                             final String name,
                             final boolean mustExist) throws IOException {
        if (ObjectId.zeroId().equals(id)) {
            return null;
        }

        try {
            RevObject o = rw.parseAny(id);
            if (o instanceof RevTag) {
                RevObject p = rw.peel(o);
                return new ObjectIdRef.PeeledTag(NETWORK,
                                                 name,
                                                 id,
                                                 p.copy());
            }
            return new ObjectIdRef.PeeledNonTag(NETWORK,
                                                name,
                                                id);
        } catch (MissingObjectException e) {
            if (mustExist) {
                throw e;
            }
            return new ObjectIdRef.Unpeeled(NETWORK,
                                            name,
                                            id);
        }
    }

    interface BiFunction {

        boolean apply(final ObjectReader reader,
                      final RefTree refTree) throws IOException;
    }

    private void commit(final Repository repo,
                        final RevCommit original,
                        final BiFunction fun) throws IOException {
        try (final ObjectReader reader = repo.newObjectReader();
             final ObjectInserter inserter = repo.newObjectInserter();
             final RevWalk rw = new RevWalk(reader)) {

            final RefTreeDatabase refdb = (RefTreeDatabase) repo.getRefDatabase();
            final RefDatabase bootstrap = refdb.getBootstrap();
            final RefUpdate refUpdate = bootstrap.newUpdate(refdb.getTxnCommitted(),
                                                            false);

            final CommitBuilder cb = new CommitBuilder();
            final Ref ref = bootstrap.exactRef(refdb.getTxnCommitted());
            final RefTree tree;
            if (ref != null && ref.getObjectId() != null) {
                tree = RefTree.read(reader,
                                    rw.parseTree(ref.getObjectId()));
                cb.setParentId(ref.getObjectId());
                refUpdate.setExpectedOldObjectId(ref.getObjectId());
            } else {
                tree = RefTree.newEmptyTree();
                refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
            }

            if (fun.apply(reader,
                          tree)) {
                final Ref ref2 = bootstrap.exactRef(refdb.getTxnCommitted());
                if (ref2 == null || ref2.getObjectId().equals(ref != null ? ref.getObjectId() : null)) {
                    cb.setTreeId(tree.writeTree(inserter));
                    if (original != null) {
                        cb.setAuthor(original.getAuthorIdent());
                        cb.setCommitter(original.getAuthorIdent());
                    } else {
                        final PersonIdent personIdent = new PersonIdent("user",
                                                                        "user@example.com");
                        cb.setAuthor(personIdent);
                        cb.setCommitter(personIdent);
                    }
                    refUpdate.setNewObjectId(inserter.insert(cb));
                    inserter.flush();
                    final RefUpdate.Result result = refUpdate.update(rw);
                    switch (result) {
                        case NEW:
                        case FAST_FORWARD:
                            break;
                        default:
                            throw new RuntimeException(repo.getDirectory() + " -> " + result.toString() + " : " + refUpdate.getName());
                    }
                    final File commited = new File(repo.getDirectory(),
                                                   refdb.getTxnCommitted());
                    final File accepted = new File(repo.getDirectory(),
                                                   refdb.getTxnNamespace() + "accepted");
                    Files.copy(commited.toPath(),
                               accepted.toPath(),
                               StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
