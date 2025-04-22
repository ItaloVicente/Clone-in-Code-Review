			recursiveDelete(folder1);
			recursiveDelete(folder2);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt")
					.addFilepattern("folder2/file1.txt")
					.addFilepattern("folder2/file2.txt").call();
			RevCommit other = git.commit()
					.setMessage("removing folders on 'branch'").call();
