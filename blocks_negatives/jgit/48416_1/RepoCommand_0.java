
				command.addSubmodule(remoteUrl + proj.name,
						proj.path,
						proj.revision == null
								? defaultRevision : proj.revision,
						proj.copyfiles);
