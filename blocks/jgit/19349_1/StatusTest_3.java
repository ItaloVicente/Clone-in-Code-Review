
	@Test
	public void testStatusPorcelain() throws Exception {
		Git git = new Git(db);
		writeTrashFile("tracked"
		writeTrashFile("stagedNew"
		writeTrashFile("stagedModified"
		writeTrashFile("stagedDeleted"
		writeTrashFile("trackedModified"
		writeTrashFile("trackedDeleted"
		writeTrashFile("untracked"
						"?? stagedDeleted"
						"?? stagedModified"
						"?? stagedNew"
						"?? tracked"
						"?? trackedDeleted"
						"?? trackedModified"
						"?? untracked"
				}
		git.add().addFilepattern("tracked").call();
		git.add().addFilepattern("stagedModified").call();
		git.add().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("trackedModified").call();
		git.add().addFilepattern("trackedDeleted").call();
						"A  stagedDeleted"
						"A  stagedModified"
						"A  tracked"
						"A  trackedDeleted"
						"A  trackedModified"
						"?? stagedNew"
						"?? untracked"
				}
		git.commit().setMessage("initial commit").call();
						"?? stagedNew"
						"?? untracked"
				}
		writeTrashFile("stagedModified"
		deleteTrashFile("stagedDeleted");
		writeTrashFile("trackedModified"
		deleteTrashFile("trackedDeleted");
		git.add().addFilepattern("stagedModified").call();
		git.rm().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("stagedNew").call();
						"D  stagedDeleted"
						"M  stagedModified"
						"A  stagedNew"
						" D trackedDeleted"
						" M trackedModified"
						"?? untracked"
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.add().addFilepattern("trackedModified").call();
		git.rm().addFilepattern("trackedDeleted").call();
		git.commit().setMessage("commit before branching").call();
						"?? untracked"
				}
		git.checkout().setCreateBranch(true).setName("test").call();
						"?? untracked"
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		RevCommit testBranch = git.commit()
				.setMessage("changed unmerged in test branch").call();
						"?? untracked"
				}
		git.checkout().setName("master").call();
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.commit().setMessage("changed unmerged in master branch").call();
						"?? untracked"
				}
		git.merge().include(testBranch.getId()).call();
						"UU unmerged"
						"?? untracked"
				}
		String commitId = db.getRef(Constants.MASTER).getObjectId().name();
		git.checkout().setName(commitId).call();
						"UU unmerged"
						"?? untracked"
				}
	}
