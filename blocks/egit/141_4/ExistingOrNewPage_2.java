			IPath container = m.getContainerPath();
			if (!container.isEmpty())
				container = container.addTrailingSeparator();
			IPath relativePath = container.append(m.getGitDir());
			if (isAlternative)
				treeItem2.setText(0, relativePath.removeLastSegments(1).addTrailingSeparator().toString());
			treeItem2.setText(2, relativePath.toString());
