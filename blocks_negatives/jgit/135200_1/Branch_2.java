				if (!dst.startsWith(Constants.R_HEADS))
					dst = Constants.R_HEADS + dst;
				if (!Repository.isValidRefName(dst))
					throw die(MessageFormat.format(CLIText.get().notAValidRefName, dst));

				RefRename r = db.renameRef(src, dst);
				if (r.rename() != Result.RENAMED)
					throw die(MessageFormat.format(CLIText.get().cannotBeRenamed, src));
