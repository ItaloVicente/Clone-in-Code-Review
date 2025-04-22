			BareSuperprojectWriter writer = new BareSuperprojectWriter(repo
					targetBranch
					author == null ? new PersonIdent(repo) : author
					callback == null ? new DefaultRemoteReader() : callback
					bareWriterConfig);
			return writer.write(renamedProjects);
