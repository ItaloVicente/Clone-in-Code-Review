	private void decode() {
		if (raw != null) {
			try {
				DataInputStream br = new DataInputStream(new ByteArrayInputStream(raw));
				String n = br.readLine();
				if (n == null || !n.startsWith("tree ")) {
					throw new CorruptObjectException(commitId, JGitText.get().corruptObjectNotree);
				}
				while ((n = br.readLine()) != null && n.startsWith("parent ")) {
				}
				if (n == null || !n.startsWith("author ")) {
					throw new CorruptObjectException(commitId, JGitText.get().corruptObjectNoAuthor);
				}
				String rawAuthor = n.substring("author ".length());
				n = br.readLine();
				if (n == null || !n.startsWith("committer ")) {
					throw new CorruptObjectException(commitId, JGitText.get().corruptObjectNoCommitter);
				}
				String rawCommitter = n.substring("committer ".length());
				n = br.readLine();
				if (n != null && n.startsWith(	"encoding"))
					encoding = Charset.forName(n.substring("encoding ".length()));
				else
					if (n == null || !n.equals("")) {
						throw new CorruptObjectException(commitId, MessageFormat.format(
								JGitText.get().corruptObjectMalformedHeader, n));
				}
				br.read(readBuf);
				int msgstart = readBuf.length != 0 ? ( readBuf[0] == '\n' ? 1 : 0 ) : 0;

				if (encoding == null) encoding = Constants.CHARSET;

				author = new PersonIdent(new String(rawAuthor.getBytes(),encoding.name()));
				committer = new PersonIdent(new String(rawCommitter.getBytes(),encoding.name()));
				message = new String(readBuf,msgstart, readBuf.length-msgstart, encoding.name());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				raw = null;
			}
		}
