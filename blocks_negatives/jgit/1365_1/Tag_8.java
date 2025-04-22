	private void decode() {
		if (raw != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new ByteArrayInputStream(raw)));
				String n = br.readLine();
				if (n == null || !n.startsWith("object ")) {
					throw new CorruptObjectException(tagId, JGitText.get().corruptObjectNoObject);
				}
				objId = ObjectId.fromString(n.substring(7));
				n = br.readLine();
				if (n == null || !n.startsWith("type ")) {
					throw new CorruptObjectException(tagId, JGitText.get().corruptObjectNoType);
				}
				type = n.substring("type ".length());
				n = br.readLine();

				if (n == null || !n.startsWith("tag ")) {
					throw new CorruptObjectException(tagId, JGitText.get().corruptObjectNoTagName);
				}
				tag = n.substring("tag ".length());
				n = br.readLine();

				if (n == null)
					throw new CorruptObjectException(tagId, JGitText.get().corruptObjectNoTaggerHeader);

				if (n.length()>0)
					if (n.startsWith("tagger "))
						tagger = new PersonIdent(n.substring("tagger ".length()));
					else
						throw new CorruptObjectException(tagId, JGitText.get().corruptObjectNoTaggerBadHeader);

				StringBuilder tempMessage = new StringBuilder();
				char[] readBuf = new char[2048];
				int readLen;
				while ((readLen = br.read(readBuf)) > 0) {
					tempMessage.append(readBuf, 0, readLen);
				}
				message = tempMessage.toString();
				if (message.startsWith("\n"))
					message = message.substring(1);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				raw = null;
			}
		}
