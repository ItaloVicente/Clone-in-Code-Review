			if (isAlternative) {
				IPath withoutLastSegment = relativePath.removeLastSegments(1);
				IPath path;
				if (withoutLastSegment.isEmpty())
					path = Path.fromPortableString("."); //$NON-NLS-1$
				else
					path = withoutLastSegment;
				treeItem2.setText(0, path.toString());
			}
