                git.refUpdate(targetBranch,
                              srcCommit);
            }
        } catch (final java.io.IOException e) {
            throw new IOException(new JGitInternalException(
                    MessageFormat.format(
                            JGitText.get().exceptionCaughtDuringExecutionOfCherryPickCommand,
                            e),
                    e));
        } catch (final Exception e) {
            throw new IOException(e);
        }
    }
