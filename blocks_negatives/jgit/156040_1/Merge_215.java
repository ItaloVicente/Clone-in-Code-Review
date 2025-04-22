 * Implements Git Merge command between branches in a bare repository.
 * Branches needs to be part of the same repository, you cannot merge
 * branches from different repositories (or forks).
 * This command is based on Git Cherry Pick command for a simple fast forward merge,
 * otherwise it will create a merge commit.
 * It returns the list of commits involved in the merge operation.
