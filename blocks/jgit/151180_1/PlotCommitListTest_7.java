		try (PlotWalk pw = new PlotWalk(db)) {
			pw.markStart(pw.lookupCommit(merge_fixed_logged_npe.getId()));

			PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
			pcl.source(pw);
			pcl.fillTo(Integer.MAX_VALUE);

			CommitListAssert test = new CommitListAssert(pcl);

			final int mainPos = 0;
			test.commit(merge_fixed_logged_npe)
					.parents(sort_roots
			test.commit(fix_logged_npe).parents(merge_changeset_implementation)
					.lanePos(1);
			test.commit(sort_roots).parents(merge_update_eclipse)
					.lanePos(mainPos);
			test.commit(merge_update_eclipse)
					.parents(add_a_clear
			test.commit(add_a_clear).parents(fix_broken).lanePos(mainPos);
			test.commit(fix_broken).parents(merge_disable_comment)
					.lanePos(mainPos);
			test.commit(merge_disable_comment)
					.parents(merge_resolve_handler
					.lanePos(mainPos);
			test.commit(disable_comment).parents(clone_operation).lanePos(2);
			test.commit(merge_resolve_handler)
					.parents(clone_operation
			test.commit(update_eclipse).parents(add_Maven).lanePos(3);
			test.commit(clone_operation).parents(merge_changeset_implementation)
					.lanePos(mainPos);
			test.commit(merge_changeset_implementation)
					.parents(merge_disable_source
					.lanePos(mainPos);
			test.commit(merge_disable_source)
					.parents(update_eclipse_iplog2
					.lanePos(mainPos);
			test.commit(update_eclipse_iplog2).parents(merge_use_remote)
					.lanePos(mainPos);
			test.commit(disable_source).parents(merge_use_remote).lanePos(1);
			test.commit(merge_use_remote)
					.parents(update_eclipse_iplog
			test.commit(changeset_implementation).parents(clear_repositorycache)
					.lanePos(2);
			test.commit(update_eclipse_iplog).parents(merge_add_Maven)
					.lanePos(mainPos);
			test.commit(merge_add_Maven).parents(findToolBar_layout
					.lanePos(mainPos);
			test.commit(findToolBar_layout).parents(clear_repositorycache)
					.lanePos(mainPos);
			test.commit(use_remote).parents(clear_repositorycache).lanePos(1);
			test.commit(add_Maven).parents(clear_repositorycache).lanePos(3);
			test.commit(clear_repositorycache).parents(merge_remove)
					.lanePos(mainPos);
			test.commit(resolve_handler).parents(merge_fix).lanePos(4);
			test.commit(merge_remove).parents(add_simple
					.lanePos(mainPos);
			test.commit(remove_unused).parents(merge_fix).lanePos(1);
			test.commit(add_simple).parents(merge_fix).lanePos(mainPos);
			test.commit(merge_fix).parents().lanePos(mainPos);
			test.noMoreCommits();
		}
