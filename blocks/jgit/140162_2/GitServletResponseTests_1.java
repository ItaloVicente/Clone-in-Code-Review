		gs.setRepositoryResolver((HttpServletRequest req
                    if (!name.equals(repoName))
                        throw new RepositoryNotFoundException(name);
                    
                    final Repository db = srv.getRepository();
                    db.incrementOpen();
                    return db;
                });
