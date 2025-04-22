	                            control.dispose();
	                        }
	                        availableShells.add(s);
	                        s.setVisible(false);
                        }
                        else {
                            s.setData(CLOSE_LISTENER, l);
                        }
                    }
                }
                e.doit = false;
         }
    };

    /**
     * Creates a shell pool that allocates shells that are children of the
     * given parent and are created with the given flags.
     *
     * @param parentShell parent shell (may be null, indicating that this pool creates
     * top-level shells)
     * @param childFlags flags for all child shells
     */
    public ShellPool(Shell parentShell, int childFlags) {
        this.parentShell = parentShell;
        this.flags = childFlags;
    }

    /**
     * Returns a new shell. The shell must not be disposed directly, but it may be closed.
     * Once the shell is closed, it will be returned to the shell pool. Note: callers must
     * remove all listeners from the shell before closing it.
     */
    public Shell allocateShell(ShellListener closeListener) {
        Shell result;
        if (!availableShells.isEmpty()) {
            result = (Shell)availableShells.removeFirst();
        } else {
            result = new Shell(parentShell, flags);
            result.addShellListener(this.closeListener);
            result.addDisposeListener(disposeListener);
        }

        result.setData(CLOSE_LISTENER, closeListener);
        return result;
    }

    /**
     * Disposes this pool. Any unused shells in the pool are disposed immediately,
     * and any shells in use will be disposed once they are closed.
     *
     * @since 3.1
     */
    public void dispose() {
        for (Iterator iter = availableShells.iterator(); iter.hasNext();) {
            Shell next = (Shell) iter.next();
            next.removeDisposeListener(disposeListener);

            next.dispose();
        }

        availableShells.clear();
        isDisposed = true;
    }
