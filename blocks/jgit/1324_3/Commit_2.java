				author = RawParseUtils.parsePersonIdent(new String(rawAuthor.getBytes()
				if (author == null)
					throw new CorruptObjectException(commitId
				committer = RawParseUtils.parsePersonIdent(new String(rawCommitter.getBytes()
				if (committer == null)
					throw new CorruptObjectException(commitId
