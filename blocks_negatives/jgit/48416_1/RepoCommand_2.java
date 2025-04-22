				for (CopyFile copyfile : copyfiles) {
					copyfile.copy();
					git.add().addFilepattern(copyfile.dest).call();
				}
			} catch (GitAPIException e) {
				throw new SAXException(e);
			} catch (IOException e) {
				throw new SAXException(e);
