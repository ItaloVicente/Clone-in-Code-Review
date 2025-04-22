		commit((ObjectReader reader
                    Ref old = tree.exactRef(reader
                    Command n;
                    try (RevWalk rw = new RevWalk(repo)) {
                        n = new Command(old
                                Command.toRef(rw
                    }
                    return tree.apply(Collections.singleton(n));
                });
