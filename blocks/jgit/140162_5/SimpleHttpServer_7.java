		gs.setRepositoryResolver((HttpServletRequest req
                    if (!name.equals(nameOf(db)))
                        throw new RepositoryNotFoundException(name);
                    
                    db.incrementOpen();
                    return db;
                });
