			List<String> bugs = commit.getFooterLines(BUG);
			if (1 == bugs.size()) {
				item.setBugID(bugs.get(0));

			} else if (2 <= bugs.size()) {
				StringBuilder tmp = new StringBuilder();
				for (String bug : bugs) {
					if (tmp.length() > 0)
						tmp.append(",);
-					tmp.append(bug);
-				}
-				item.setBugID(tmp.toString());
-			}
-
 			if (2 <= cnt) {
 				item.setSize((merge)");
