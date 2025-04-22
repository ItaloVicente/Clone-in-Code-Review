
			ObjectId cid;
			if (changeId.equals(""))
				cid = ChangeIdUtil.computeChangeId(c.getTreeId()
						c.getAuthor()
			else
				cid = ObjectId.fromString(changeId);
			message = ChangeIdUtil.insertId(message
			if (cid != null)
