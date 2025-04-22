                                           if (maxToRemove > 0) {
                                               maxToRemove--;
                                           } else {
                                               LOGGER.debug(
                                                   "Would remove {}, but minimum threshold reached, ignoring for this run.",
                                                   logIdent(hostname, PooledService.this)
                                               );
                                               continue;
                                           }
