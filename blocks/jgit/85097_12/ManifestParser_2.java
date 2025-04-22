			List<LinkFile> linkfiles = new ArrayList<>(proj.getLinkFiles());
			proj.clearLinkFiles();
			for (LinkFile linkfile : linkfiles) {
				if (!isNestedReferencefile(linkfile)) {
					proj.addLinkFile(linkfile);
				}
			}
