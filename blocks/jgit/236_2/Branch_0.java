			if (rename) {
				String src
				if (branches.size() == 1) {
					final Ref head = db.getRef(Constants.HEAD);
					if (head != null && head.isSymbolic())
						src = head.getLeaf().getName();
					else
						throw die("Cannot rename detached HEAD");
					dst = branches.get(0);
				} else {
					src = branches.get(0);
					final Ref old = db.getRef(src);
					if (old == null)
						throw die(String.format("%s does not exist"
					if (!old.getName().startsWith(Constants.R_HEADS))
						throw die(String.format("%s is not a branch"
					src = old.getName();
					dst = branches.get(1);
				}

				if (!dst.startsWith(Constants.R_HEADS))
					dst = Constants.R_HEADS + dst;
				if (!Repository.isValidRefName(dst))
					throw die(String.format("%s is not a valid ref name"

				RefRename r = db.renameRef(src
				if (r.rename() != Result.RENAMED)
					throw die(String.format("%s cannot be renamed"

			} else if (branches.size() > 0) {
