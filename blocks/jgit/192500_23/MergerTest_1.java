package org.eclipse.jgit.merge;

import static org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason.DIRTY_INDEX;
import static org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason.DIRTY_WORKTREE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Strings;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MergerRenameTest extends RepositoryTestCase {

	@DataPoints
	public static MergeStrategy[] strategiesUnderTest = new MergeStrategy[] {
			MergeStrategy.RECURSIVE };

	@Before
	public void enableRename() throws IOException
		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_RENAMES
		config.setString(ConfigConstants.CONFIG_MERGE_SECTION
				ConfigConstants.CONFIG_KEY_RENAMES
		config.save();
	}

	public void testRename_merged(MergeStrategy strategy
			String originalContent
			String theirsName
			Map<String
		Map<String
		originalFiles.put(originalName
		Map<String
		oursFiles.put(oursName
		Map<String
		theirsFiles.put(theirsName
		testRename_merged(strategy
				expectedFileContents);
	}

	public void testRename_merged(MergeStrategy strategy
			Map<String
			Map<String
			Map<String
			Map<String
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}
		MergeResult mergeResult = mergeRename(strategy
				oursFilesToContents
		assertEquals(mergeResult.getMergeStatus()
		Set<String> expectedIndexContent = new HashSet<>();
		for (Entry<String
				.entrySet()) {
			if (expectedFile.getValue() != null) {
				assertEquals(expectedFile.getValue()
						read(expectedFile.getKey()));
				expectedIndexContent.add(String.format(
						"%s
						expectedFile.getValue()));
			} else {
				assertFalse(check(expectedFile.getKey()));
			}
		}
		Set<String> stagedFiles = Arrays
				.asList(indexState(CONTENT).split("\\[|\\]")).stream()
				.filter(s -> !Strings.isNullOrEmpty(s))
				.collect(Collectors.toSet());
		assertEquals(stagedFiles
	}

	public void testRename_withConflict(MergeStrategy strategy
			Map<String
			Map<String
			Map<String
			Map<String
			Set<String> expectedConflicts) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}
		MergeResult mergeResult = mergeRename(strategy
				oursFilesToContents
		assertEquals(mergeResult.getMergeStatus()
		assertTrue(mergeResult.getFailingPaths() == null);
		assertEquals(mergeResult.getConflicts().keySet()
		for (Entry<String
				.entrySet()) {
			if (expectedFile.getValue() == null) {
				assertFalse(check(expectedFile.getKey()));
			} else if (expectedFile.getValue().startsWith("<<<<<<<")) {
				assertTrue(read(expectedFile.getKey())
						.contains(expectedFile.getValue()));
			} else {
				assertEquals(expectedFile.getValue()
						read(expectedFile.getKey()));
			}
		}
	}

	public void testRename_withConflict(MergeStrategy strategy
			Map<String
			Map<String
			Map<String
			Map<String
			Set<String> expectedConflicts
			throws Exception {

		testRename_withConflict(strategy
				oursFilesToContents
				expectedFileContents

		Set<String> stagedFiles = Arrays
				.asList(indexState(CONTENT).split("\\[|\\]")).stream()
				.filter(s -> !Strings.isNullOrEmpty(s))
				.collect(Collectors.toSet());
		assertEquals(stagedFiles
	}

	private RevCommit setUpMerge(Git git
			Map<String
			Map<String
			Map<String
		for (Entry<String
				.entrySet()) {
			writeTrashFile(originalFile.getKey()
			git.add().addFilepattern(originalFile.getKey()).call();
		}
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI)
				.setName("second-branch").call();
		for (Entry<String
				.entrySet()) {
			git.rm().addFilepattern(originalFile.getKey()).call();
		}
		for (Entry<String
				.entrySet()) {
			writeTrashFile(theirsFile.getKey()
			git.add().addFilepattern(theirsFile.getKey()).call();
		}
		RevCommit mergeCommit = git.commit()
				.setMessage("Commit on second-branch").call();

		git.checkout().setName("master").call();

		for (Entry<String
				.entrySet()) {
			git.rm().addFilepattern(originalFile.getKey()).call();
		}
		for (Entry<String
			writeTrashFile(oursFile.getKey()
			git.add().addFilepattern(oursFile.getKey()).call();
		}

		RevCommit headCommit = git.commit().setMessage("Commit on master")
				.call();
		return mergeCommit;
	}

	private MergeResult mergeRename(MergeStrategy strategy
			Map<String
			Map<String
			Map<String
		Git git = Git.wrap(db);
		RevCommit mergeCommit = setUpMerge(git
				oursFilesToContents

		return git.merge().include(mergeCommit).setStrategy(strategy).call();
	}


	@DataPoints("renameLists")
	public static final List<List<Entry<String
			.of(
					List.of(Map.entry("test/file2"
					List.of(Map.entry("test/file1"
					List.of(Map.entry("test/a/file1"
					List.of(Map.entry("test/z/file1"
					List.of(Map.entry("test/file1"
					List.of(Map.entry("test/file1"
					List.of(Map.entry("test/file1"
					List.of(Map.entry("test/file1"
					List.of(Map.entry("test/w/file1"
					List.of(Map.entry("test/a/file1"
					List.of(Map.entry("test/w/file1"
					List.of(Map.entry("test/a/file1"

	public void testRenameModify_merged(MergeStrategy strategy
			List<Entry<String
			throws Exception {

		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		Map<String
		for (Entry<String
			originalFiles.put(renamePair.getKey()
					originalContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			noRenameFiles.put(renamePair.getKey()
					slightlyModifiedContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			renameFiles.put(renamePair.getValue()
					originalContent + renamePair.getKey());
		}
		Map<String

		for (Entry<String
			expectedFiles.put(renamePair.getValue()
					slightlyModifiedContent + renamePair.getKey());
		}
		testRename_merged(strategy
				isRenameInOurs ? renameFiles : noRenameFiles
				isRenameInOurs ? noRenameFiles : renameFiles
	}

	@Theory
	public void checkRenameModifyFile_merged(MergeStrategy strategy
			@FromDataPoints("renameLists") List<Entry<String
			boolean isRenameInOurs) throws Exception {
		testRenameModify_merged(strategy

	}

	@DataPoints("renameListsSplit")
	public static final List<List<Entry<String
			.of(
					List.of(Map.entry("test/a/file1"
							Map.entry("test/a/file2"
					List.of(Map.entry("test/a/file1"
							Map.entry("test/a/file2"
					List.of(Map.entry("test/a/file1"
							Map.entry("test/a/file2"

					List.of(Map.entry("test/a/file1"
							Map.entry("test/a/file2"
					List.of(Map.entry("test/a/file1"
							Map.entry("test/a/file2"

	@Theory
	public void checkRenameSplitDir_merged(MergeStrategy strategy
			@FromDataPoints("renameListsSplit") List<Entry<String
			boolean isRenameInOurs) throws Exception {
		testRenameModify_merged(strategy

	}

	@Theory
	public void checkRenameModify_merged(MergeStrategy strategy
			boolean isRenameInOurs
			boolean modifyInOther) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "z\na\nb\nc";

		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		renameFiles.put(renameFilename
				modifyInRename ? slightlyModifiedContent1 : originalContent);
		Map<String
		otherFiles.put(originalFilename
				modifyInOther ? slightlyModifiedContent2 : originalContent);

		String expectedRenameContent = modifyInRename && modifyInOther
				? "z\na\nb\nc\nd"
				: modifyInRename ? slightlyModifiedContent1
						: modifyInOther ? slightlyModifiedContent2
								: originalContent;
		Map<String
		expectedFiles.put(renameFilename
		expectedFiles.put(originalFilename

		testRename_merged(strategy
				isRenameInOurs ? renameFiles : otherFiles
				isRenameInOurs ? otherFiles : renameFiles
	}

	@Theory
	public void checkRenameModify_conflict(MergeStrategy strategy
			boolean isRenameInOurs) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String oursContent = "a\nb\nc\nx";

		String theirsContent = "a\nb\nc\nd";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		renameFiles.put(renameFilename
				isRenameInOurs ? oursContent : theirsContent);
		Map<String
		otherFiles.put(originalFilename
				isRenameInOurs ? theirsContent : oursContent);

		Map<String
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n" + "x\n" + "=======\n" + "d\n");
		expectedFiles.put(originalFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename

		testRename_withConflict(strategy
				isRenameInOurs ? renameFiles : otherFiles
				isRenameInOurs ? otherFiles : renameFiles
				expectedConflicts
	}

	@Theory
	public void checkRenameBothModify_merged(MergeStrategy strategy
			boolean isRenameInOurs
			boolean modifyInOther) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "z\na\nb\nc";

		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		renameFiles.put(renameFilename
				modifyInRename ? slightlyModifiedContent1 : originalContent);
		Map<String
		otherFiles.put(renameFilename
				modifyInOther ? slightlyModifiedContent2 : originalContent);

		String expectedRenameContent = modifyInRename && modifyInOther
				? "z\na\nb\nc\nd"
				: modifyInRename ? slightlyModifiedContent1
						: modifyInOther ? slightlyModifiedContent2
								: originalContent;
		Map<String
		expectedFiles.put(renameFilename
		expectedFiles.put(originalFilename

		testRename_merged(strategy
				isRenameInOurs ? renameFiles : otherFiles
				isRenameInOurs ? otherFiles : renameFiles
	}

	@Theory
	public void checkRenameBothModify_conflict(MergeStrategy strategy)
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String oursContent = "a\nb\nc\nx";

		String theirsContent = "a\nb\nc\nd";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		oursFiles.put(renameFilename
		Map<String
		theirsFiles.put(renameFilename

		Map<String
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n" + "x\n" + "=======\n" + "d\n");
		expectedFiles.put(originalFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename

		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkCrossSidesRename_notDetected_merged(MergeStrategy strategy)
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "Unrelated base file";
		String renameFileContent = "Identical merge-side-file";
		String oursFilename = "test/file2";
		String theirsFilename = "test/file3";
		Map<String
		originalFiles.put(originalFilename
		Map<String
		oursFiles.put(oursFilename
		Map<String
		theirsFiles.put(theirsFilename

		Map<String
		expectedFiles.put(oursFilename
		expectedFiles.put(theirsFilename
		expectedFiles.put(originalFilename

		testRename_merged(strategy
				expectedFiles);
	}

	@Theory
	public void checkRenameWithReplace_merged(MergeStrategy strategy
			boolean isRenameInOurs) throws Exception {

		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename

		Map<String
		expectedFiles.put(renameFilename
		expectedFiles.put(originalFilename

		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameWithReplace_conflict(MergeStrategy strategy
			boolean isRenameInOurs) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);

		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename

		Map<String
		expectedFiles.put(renameFilename
				String.format(
						"<<<<<<< HEAD\n%s\n" + "=======" + "\n%s\n>>>>>>>"
						isRenameInOurs ? slightlyModifiedContent
								: "Unrelated content"
						isRenameInOurs ? "Unrelated content"
								: slightlyModifiedContent));
		expectedFiles.put(originalFilename

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles
	}


	@Theory
	public void checkRenameRename_conflict(MergeStrategy strategy
			boolean modifyInOurs
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String oursFilename = "test/file2";
		String oursContent = modifyInOurs ? slightlyModifiedContent
				: originalContent;
		String theirsFilename = "test/file3";
		String theirsContent = modifyInTheirs ? slightlyModifiedContent
				: originalContent;

		Map<String
		originalFiles.put(originalFilename
		Map<String
		oursFiles.put(oursFilename
		Map<String
		theirsFiles.put(theirsFilename

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(oursFilename
		expectedFiles.put(theirsFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(originalFilename);
		expectedConflicts.add(oursFilename);
		expectedConflicts.add(theirsFilename);

		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				oursFilename
		expectedIndex.add(String.format("%s
				theirsFilename
		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkRenameDelete_conflict(MergeStrategy strategy
			boolean isRenameInOurs
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		Map<String
		filesWithRename.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int deleteStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				shouldModify ? slightlyModifiedContent : originalContent));

		Map<String
		expectedFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		expectedFiles.put(originalFilename

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithDelete
				isRenameInOurs ? filesWithDelete : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkDelete_conflict(MergeStrategy strategy
			boolean isRenameInOurs) throws Exception {

		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		Map<String
		filesWithRename.put(originalFilename
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(originalFilename);
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int deleteStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				originalFilename

		Map<String
		expectedFiles.put(originalFilename

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithDelete
				isRenameInOurs ? filesWithDelete : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkRenameAddCollision_conflict(MergeStrategy strategy
			boolean isRenameInOurs
			boolean shouldModifyOriginal) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(renameFilename
		filesWithModify.put(originalFilename
				shouldModifyOriginal ? slightlyModifiedContent
						: originalContent);
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyRename ? slightlyModifiedContent : originalContent);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		if (shouldModifyOriginal) {
			expectedConflicts.add(originalFilename);
		}
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		if (shouldModifyOriginal) {
			expectedIndex
					.add(String.format("%s
							originalFilename
			expectedIndex.add(String.format(
					"%s
					modifyStage
		}
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				filesWithRename.get(renameFilename)));

		Map<String
		String oursContent = isRenameInOurs
				? filesWithRename.get(renameFilename)
				: "Unrelated file";
		String theirsContent = isRenameInOurs ? "Unrelated file"
				: filesWithRename.get(renameFilename);
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n%s\n=======\n%s"
		if (shouldModifyOriginal) {
			expectedFiles.put(originalFilename
					shouldModifyOriginal ? slightlyModifiedContent
							: originalContent);
		}

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkRenameModifyCollision_collisionUnchanged_merged(
			MergeStrategy strategy
			boolean shouldModifyRename
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		filesWithModify.put(originalFilename
				shouldModifyOriginal ? slightlyModifiedContent
						: originalContent);
		filesWithModify.put(renameFilename
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyRename ? slightlyModifiedContent : originalContent);
		Map<String
		expectedFiles.put(renameFilename
				shouldModifyRename || shouldModifyOriginal
						? slightlyModifiedContent
						: originalContent);
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameModifyCollision_collisionUnchanged_conflicting(
			MergeStrategy strategy
			boolean shouldModifyRename
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		filesWithModify.put(renameFilename
		filesWithModify.put(originalFilename
				shouldModifyOriginal ? slightlyModifiedContent
						: originalContent);
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyRename ? slightlyModifiedContent : originalContent);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		if (shouldModifyOriginal) {
			expectedConflicts.add(originalFilename);
		}
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		if (shouldModifyOriginal) {
			expectedIndex
					.add(String.format("%s
							originalFilename
			expectedIndex.add(String.format(
					"%s
					modifyStage
		}
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				filesWithRename.get(renameFilename)));

		Map<String
		String oursContent = isRenameInOurs
				? filesWithRename.get(renameFilename)
				: "Unrelated file";
		String theirsContent = isRenameInOurs ? "Unrelated file"
				: filesWithRename.get(renameFilename);
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n%s\n=======\n%s"
		if (shouldModifyOriginal) {
			expectedFiles.put(originalFilename
					shouldModifyOriginal ? slightlyModifiedContent
							: originalContent);
		}

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkRenameAddCollision_withRenameDelete_conflict(
			MergeStrategy strategy
			boolean shouldModifyRename) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(renameFilename
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyRename ? slightlyModifiedContent : originalContent);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;

		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				shouldModifyRename ? slightlyModifiedContent
						: originalContent));

		Map<String

		String oursContent = isRenameInOurs
				? filesWithRename.get(renameFilename)
				: filesWithModify.get(renameFilename);
		String theirsContent = isRenameInOurs
				? filesWithModify.get(renameFilename)
				: filesWithRename.get(renameFilename);
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n%s\n=======\n%s"
		expectedFiles.put(originalFilename

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkFilesNamesSwappedOnSides_conflict(MergeStrategy strategy
			boolean isRenameInOurs
			boolean shouldModifyOriginal) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		filesWithModify.put(originalFilename
				shouldModifyOriginal ? slightlyModifiedContent
						: originalContent);
		filesWithModify.put(renameFilename
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyRename ? slightlyModifiedContent : originalContent);
		filesWithRename.put(originalFilename

		Map<String
				: filesWithModify;
		Map<String
				: filesWithRename;
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		if (shouldModifyOriginal) {
			expectedConflicts.add(originalFilename);
		}
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		if (shouldModifyOriginal) {
			expectedIndex
					.add(String.format("%s
							originalFilename
			expectedIndex.add(String.format(
					"%s
					modifyStage
							: originalContent));
			expectedIndex
					.add(String.format("%s
							originalFilename
		} else {
			expectedIndex.add(String.format("%s
					originalFilename
		}
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				shouldModifyRename ? slightlyModifiedContent
						: originalContent));

		Map<String
		expectedFiles.put(renameFilename
				String.format("<<<<<<< HEAD\n%s\n=======\n%s"
						oursFiles.get(renameFilename)
						theirsFiles.get(renameFilename)));
		if (shouldModifyOriginal) {
			expectedFiles.put(originalFilename
					String.format("<<<<<<< HEAD\n%s\n=======\n%s"
							oursFiles.get(originalFilename)
							theirsFiles.get(originalFilename)));
		} else {
			expectedFiles.put(originalFilename
		}

		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkRenameSameTarget_differentSource_conflict(
			MergeStrategy strategy
			throws Exception {
		String originalFilename1 = "test/file1";
		String originalFilename2 = "test/file2";
		String originalContent1 = "a\nb\nc";
		String originalContent2 = "x\ny\nz";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "x\ny\ny";
		String renameFilename = "test/file3";

		Map<String
		originalFiles.put(originalFilename1
		originalFiles.put(originalFilename2
		Map<String
		oursFiles.put(renameFilename
				: slightlyModifiedContent1);
		Map<String
		theirsFiles.put(renameFilename
				: slightlyModifiedContent2);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		Map<String
		expectedFiles.put(originalFilename1
		expectedFiles.put(originalFilename2
		expectedFiles.put(renameFilename
				String.format("<<<<<<< HEAD\n%s\n=======\n%s\n>>>>>>>"
						oursFiles.get(renameFilename)
						theirsFiles.get(renameFilename)));

		testRename_withConflict(strategy
				expectedFiles
	}


	@Theory
	public void checkSourceAddedOnRenameSide_merged(MergeStrategy strategy
			boolean isRenameInOurs
			boolean modifyInOther) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "z\na\nb\nc";

		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(originalFilename
				modifyInRename ? slightlyModifiedContent1 : originalContent);
		Map<String
		filesWithRename.put(renameFilename
				modifyInOther ? slightlyModifiedContent2 : originalContent);
		filesWithRename.put(originalFilename

		String expectedRenameContent = modifyInRename && modifyInOther
				? "z\na\nb\nc\nd"
				: modifyInRename ? slightlyModifiedContent1
						: modifyInOther ? slightlyModifiedContent2
								: originalContent;
		Map<String
		expectedFiles.put(renameFilename
		expectedFiles.put(originalFilename
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkSourceAdded_renameOnBothSides_merged(
			MergeStrategy strategy
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithAdd.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		filesWithAdd.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);

		Map<String
		expectedFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		expectedFiles.put(originalFilename
		testRename_merged(strategy
				isAddInOurs ? filesWithAdd : filesWithRename
				isAddInOurs ? filesWithRename : filesWithAdd
	}

	@Theory
	public void checkSourceAddedOnBothSides_renameOnBothSides_merged(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String mergeContent1 = "Content 1";

		String mergeContent2 = "Content 2";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		oursFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		oursFiles.put(originalFilename
		Map<String
		theirsFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		theirsFiles.put(originalFilename
		Map<String
		expectedFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		expectedFiles.put(originalFilename
				"<<<<<<< HEAD\n%s\n=======\n%s"
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(originalFilename);
		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkSourceAddedOnBothSides_withModification_conflict(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename
		filesWithRename.put(originalFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);

		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int modifyStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename

		String oursContent = isRenameInOurs
				? filesWithRename.get(renameFilename)
				: "Unrelated file";
		String theirsContent = isRenameInOurs ? "Unrelated file"
				: filesWithRename.get(renameFilename);
		Map<String
		expectedFiles.put(renameFilename
				"<<<<<<< HEAD\n%s\n=======\n%s"
		expectedFiles.put(originalFilename
		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkSourceAddedOnBothSides_noModification_merged(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithModify.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename
		filesWithRename.put(originalFilename

		Map<String
		expectedFiles.put(renameFilename
		expectedFiles.put(originalFilename
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameModify_withRenameExistedInBase_merged(
			MergeStrategy strategy
			boolean shouldModifyOurs
			throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		filesWithModify.put(originalFilename
				shouldModifyOurs ? slightlyModifiedContent : originalContent);
		Map<String
		filesWithRename.put(renameFilename
				shouldModifyTheirs ? slightlyModifiedContent : originalContent);

		Map<String
		expectedFiles.put(renameFilename
				shouldModifyOurs || shouldModifyTheirs ? slightlyModifiedContent
						: originalContent);

		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameBoth_withRenameExistedInBase_merged(
			MergeStrategy strategy
			boolean shouldModifyTheirs) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		oursFiles.put(renameFilename
				shouldModifyOurs ? slightlyModifiedContent : originalContent);
		Map<String
		theirsFiles.put(renameFilename
				shouldModifyTheirs ? slightlyModifiedContent : originalContent);

		Map<String
		expectedFiles.put(renameFilename
				shouldModifyOurs || shouldModifyTheirs ? slightlyModifiedContent
						: originalContent);

		testRename_merged(strategy
				expectedFiles);
	}

	@Theory
	public void checkSourceAddedOnRenameSide_withRenameDeleteConflict_conflicting(
			MergeStrategy strategy
			boolean shouldModify) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		Map<String
		filesWithRename.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		filesWithRename.put(originalFilename
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		int renameStage = isRenameInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int deleteStage = isRenameInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
				shouldModify ? slightlyModifiedContent : originalContent));

		Map<String
		expectedFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		expectedFiles.put(originalFilename

		testRename_withConflict(strategy
				isRenameInOurs ? filesWithRename : filesWithDelete
				isRenameInOurs ? filesWithDelete : filesWithRename
				expectedFiles
	}

	@Theory
	public void checkMultipleRenames_correlationOnSource_renameModify_renameDelete_conflict(
			MergeStrategy strategy
			boolean shouldRenameBothSides) throws Exception {
		shouldRenameBothSides = false;
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename = "test/file2";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename
		Map<String
		filesWithRenameSwap.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		filesWithRenameSwap.put(originalFilename
		Map<String
		filesWithRenameDelete.put(
				shouldRenameBothSides ? renameFilename : originalFilename
				shouldModify ? slightlyModifiedContent : originalContent);

		Map<String
		expectedFiles.put(renameFilename
				shouldModify ? slightlyModifiedContent : originalContent);
		expectedFiles.put(originalFilename

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(originalFilename);
		Set<String> expectedIndex = new HashSet<>();
		int renameSwapStage = isSwapInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		expectedIndex.add(String.format("%s
				renameFilename
				shouldModify ? slightlyModifiedContent : originalContent));
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				originalFilename

		testRename_withConflict(strategy
				isSwapInOurs ? filesWithRenameSwap : filesWithRenameDelete
				isSwapInOurs ? filesWithRenameDelete : filesWithRenameSwap
				expectedFiles
	}

	@Theory
	public void checkMultipleRenames_swapRename_merged(MergeStrategy strategy
			boolean shouldModifyRename1
			throws Exception {
		String renameFilename1 = "test/file1";
		String originalContent1 = "a\nb\nc";
		String slightlyModifiedContent1 = "a\nb\nb";
		String renameFilename2 = "test/file2";
		String originalContent2 = "x\ny\nz";
		String slightlyModifiedContent2 = "x\ny\ny";

		Map<String
		originalFiles.put(renameFilename1
		originalFiles.put(renameFilename2
		Map<String
		oursFilesWithRename.put(renameFilename2
				shouldModifyRename1 ? slightlyModifiedContent1
						: originalContent1);
		oursFilesWithRename.put(renameFilename1
				shouldModifyRename2 ? slightlyModifiedContent2
						: originalContent2);
		Map<String
		theirsFilesWithRename.put(renameFilename2
				shouldModifyRename1 ? slightlyModifiedContent1
						: originalContent1);
		theirsFilesWithRename.put(renameFilename1
				shouldModifyRename2 ? slightlyModifiedContent2
						: originalContent2);

		Map<String
		expectedFiles.put(renameFilename2
				shouldModifyRename1 ? slightlyModifiedContent1
						: originalContent1);
		expectedFiles.put(renameFilename1
				shouldModifyRename2 ? slightlyModifiedContent2
						: originalContent2);
		testRename_merged(strategy
				theirsFilesWithRename
	}

	@Theory
	public void checkRenameSameTarget_differentSource_wihSourcePresentOnSides_conflict(
			MergeStrategy strategy
			throws Exception {
		String originalFilename1 = "test/file1";
		String originalFilename2 = "test/file2";
		String originalContent1 = "a\nb\nc";
		String originalContent2 = "x\ny\nz";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "x\ny\ny";
		String renameFilename = "test/file3";

		Map<String
		originalFiles.put(originalFilename1
		originalFiles.put(originalFilename2
		Map<String
		oursFiles.put(renameFilename
				: slightlyModifiedContent1);
		oursFiles.put(originalFilename2
				: slightlyModifiedContent2);
		Map<String
		theirsFiles.put(renameFilename
				: slightlyModifiedContent2);
		theirsFiles.put(originalFilename1
				keepOriginalContent ? originalContent1
						: slightlyModifiedContent1);
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		if (!keepOriginalContent) {
			expectedConflicts.add(originalFilename1);
			expectedConflicts.add(originalFilename2);
		}
		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename
		if (!keepOriginalContent) {
			expectedIndex
					.add(String.format("%s
							originalFilename1
			expectedIndex
					.add(String.format("%s
							originalFilename2
			expectedIndex.add(String.format(
					"%s
					oursFiles.get(originalFilename2)));
			expectedIndex.add(String.format(
					"%s
					theirsFiles.get(originalFilename1)));
		}

		Map<String
		if (!keepOriginalContent) {
			expectedFiles.put(originalFilename1
			expectedFiles.put(originalFilename2
		} else {
			expectedFiles.put(originalFilename1
			expectedFiles.put(originalFilename2
		}
		expectedFiles.put(renameFilename
				String.format("<<<<<<< HEAD\n%s\n=======\n%s\n>>>>>>>"
						oursFiles.get(renameFilename)
						theirsFiles.get(renameFilename)));

		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkMultipleRenames_renameSameTarget_differentSource_conflict(
			MergeStrategy strategy
			throws Exception {
		String originalFilename1 = "test/file1";
		String originalFilename2 = "test/file2";
		String originalContent1 = "a\nb\nc";
		String originalContent2 = "x\ny\nz";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "x\ny\ny";
		String renameFilename = "test/file3";
		String correlatingRenameContent = "Correlating rename";
		Map<String
		originalFiles.put(originalFilename1
		originalFiles.put(originalFilename2
		originalFiles.put(renameFilename
		Map<String
		oursFiles.put(renameFilename
				: slightlyModifiedContent1);
		oursFiles.put(originalFilename1
		Map<String

		theirsFiles.put(renameFilename
				: slightlyModifiedContent2);
		theirsFiles.put(originalFilename1
		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename);
		Set<String> expectedIndex = new HashSet<>();
		expectedIndex.add(String.format("%s
				originalFilename1
		expectedIndex.add(String.format("%s
				renameFilename
		expectedIndex.add(String.format("%s
				renameFilename

		Map<String
		expectedFiles.put(renameFilename
				String.format("<<<<<<< HEAD\n%s\n=======\n%s\n>>>>>>>"
						oursFiles.get(renameFilename)
						theirsFiles.get(renameFilename)));
		expectedFiles.put(originalFilename1

		testRename_withConflict(strategy
				expectedFiles
	}

	@Theory
	public void checkRenameRename_withSourceAdded_conflict(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String renameFilename1 = "test/file2";
		String renameFilename2 = "test/file3";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithAdd.put(renameFilename1
		filesWithAdd.put(originalFilename
		Map<String
		filesWithRename.put(renameFilename2

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(renameFilename1
		expectedFiles.put(renameFilename2
		testRename_merged(strategy
				addInOurs ? filesWithAdd : filesWithRename
				addInOurs ? filesWithRename : filesWithAdd
	}

	@Theory
	public void checkRenameRename_withSourceAddedOnBothSides_merged(
			MergeStrategy strategy) throws Exception {
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename1 = "test/file2";
		String renameFilename2 = "test/file3";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		oursFiles.put(originalFilename
		oursFiles.put(renameFilename1
		Map<String
		theirsFile.put(originalFilename
		theirsFile.put(renameFilename2

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(renameFilename1
		expectedFiles.put(renameFilename2
		testRename_merged(strategy
				expectedFiles);
	}

	@Theory
	public void checkRenameRename_withRenameAddCollision_conflicting(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename1 = "test/file2";
		String renameFilename2 = "test/file3";

		Map<String
		originalFiles.put(originalFilename
		Map<String
		filesWithAdd.put(renameFilename1
		filesWithAdd.put(renameFilename2
		Map<String
		filesWithRename.put(renameFilename2

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename2);
		Set<String> expectedIndex = new HashSet<>();
		int modifyStage = isAddInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int renameStage = isAddInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				renameFilename1
		expectedIndex.add(String.format("%s
				renameFilename2
		expectedIndex.add(String.format("%s
				renameFilename2

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(renameFilename1
		String oursContent = isAddInOurs ? "Unrelated file"
				: slightlyModifiedContent2;
		String theirsContent = isAddInOurs ? slightlyModifiedContent2
				: "Unrelated file";
		expectedFiles.put(renameFilename2
				"<<<<<<< HEAD\n%s\n=======\n%s"
		testRename_withConflict(strategy
				isAddInOurs ? filesWithAdd : filesWithRename
				isAddInOurs ? filesWithRename : filesWithAdd
				expectedConflicts
	}

	@Theory
	public void checkRenameRename_withRenameModifyCollision_conflicting(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename1 = "test/file2";
		String renameFilename2 = "test/file3";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename2

		Map<String
		filesWithAdd.put(renameFilename1
		filesWithAdd.put(renameFilename2
		Map<String
		filesWithRename.put(renameFilename2

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(renameFilename2);
		Set<String> expectedIndex = new HashSet<>();
		int modifyStage = isAddInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int renameStage = isAddInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				renameFilename1
		expectedIndex.add(String.format("%s
				renameFilename2
		expectedIndex.add(String.format("%s
				renameFilename2
		expectedIndex.add(String.format("%s
				renameFilename2

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(renameFilename1
		String oursContent = isAddInOurs ? "Unrelated file"
				: slightlyModifiedContent2;
		String theirsContent = isAddInOurs ? slightlyModifiedContent2
				: "Unrelated file";
		expectedFiles.put(renameFilename2
				"<<<<<<< HEAD\n%s\n=======\n%s"
		testRename_withConflict(strategy
				isAddInOurs ? filesWithAdd : filesWithRename
				isAddInOurs ? filesWithRename : filesWithAdd
				expectedConflicts
	}

	@Theory
	public void checkRenameRename_withRenameCollision_conflicting(
			MergeStrategy strategy
		String originalFilename = "test/file1";
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent1 = "a\nb\nb";
		String slightlyModifiedContent2 = "a\nb\nc\nd";
		String renameFilename1 = "test/file2";
		String renameFilename2 = "test/file3";

		Map<String
		originalFiles.put(originalFilename
		originalFiles.put(renameFilename2

		Map<String
		filesWithAdd.put(renameFilename1
		filesWithAdd.put(renameFilename2
		Map<String
		filesWithRename.put(renameFilename2

		Set<String> expectedConflicts = new HashSet<>();
		expectedConflicts.add(originalFilename);
		expectedConflicts.add(renameFilename1);
		expectedConflicts.add(renameFilename2);
		Set<String> expectedIndex = new HashSet<>();
		int modifyStage = isAddInOurs ? DirCacheEntry.STAGE_2
				: DirCacheEntry.STAGE_3;
		int renameStage = isAddInOurs ? DirCacheEntry.STAGE_3
				: DirCacheEntry.STAGE_2;
		expectedIndex.add(String.format("%s
				originalFilename
		expectedIndex.add(String.format("%s
				renameFilename1
		expectedIndex.add(String.format("%s
				renameFilename2

		Map<String
		expectedFiles.put(originalFilename
		expectedFiles.put(renameFilename1
		expectedFiles.put(renameFilename2
		testRename_withConflict(strategy
				isAddInOurs ? filesWithAdd : filesWithRename
				isAddInOurs ? filesWithRename : filesWithAdd
				expectedConflicts
	}


	@Theory
	public void checkRenameRename_dirtyIndex_fail(MergeStrategy strategy
			boolean dirtyOriginal
			throws Exception {
		if (!dirtyOriginal && !dirtyOurs && !dirtyTheirs) {
			return;
		}
		Git git = Git.wrap(db);
		String content = "Content";
		String originalFilename = "file1";
		String renameFilename1 = "file2";
		String renameFilename2 = "file3";

		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				Map.of(renameFilename1
				Map.of(renameFilename2

		String failingPath = null;
		if (dirtyTheirs) {
			writeTrashFile(renameFilename2
			git.add().addFilepattern(renameFilename2).call();
			failingPath = renameFilename2;
		}
		if (dirtyOurs) {
			writeTrashFile(renameFilename1
			git.add().addFilepattern(renameFilename1).call();
			failingPath = renameFilename1;
		}
		if (dirtyOriginal) {
			writeTrashFile(originalFilename
			git.add().addFilepattern(originalFilename).call();
			failingPath = originalFilename;
		}

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()

		assertEquals(mergeResult.getFailingPaths()
				Map.of(failingPath
		if (dirtyOriginal) {
			assertEquals(read(originalFilename)
		}
		if (dirtyOurs) {
			assertEquals(read(renameFilename1)
		}
		if (dirtyTheirs) {
			assertEquals(read(renameFilename2)
		}
	}

	@Theory
	public void checkRenameRename_dirtyWorkingTree_fail(MergeStrategy strategy
			boolean dirtyOriginal
			throws Exception {
		Git git = Git.wrap(db);
		String content = "Content";
		String originalFilename = "file1";
		String renameFilename1 = "file2";
		String renameFilename2 = "file3";

		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				Map.of(renameFilename1
				Map.of(renameFilename2

		String failingPath = null;
		if (dirtyTheirs) {
			writeTrashFile(renameFilename2
			failingPath = renameFilename2;
		}
		if (dirtyOurs) {
			writeTrashFile(renameFilename1
			failingPath = renameFilename1;
		}
		if (dirtyOriginal) {
			writeTrashFile(originalFilename
		}

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		if (dirtyOurs || dirtyTheirs) {
			assertEquals(mergeResult.getMergeStatus()
			assertEquals(mergeResult.getFailingPaths()
					Map.of(failingPath
		} else {
			assertEquals(mergeResult.getMergeStatus()
		}
		if (dirtyOriginal) {
			assertEquals(read(originalFilename)
		}
		if (dirtyOurs) {
			assertEquals(read(renameFilename1)
		}
		if (dirtyTheirs) {
			assertEquals(read(renameFilename2)
		}
	}

	@Theory
	public void checkRenameModify_dirtyIndex_fail(MergeStrategy strategy
			boolean isRenameInOurs
			throws Exception {
		if (!dirtyOriginal && !dirtyRename) {
			return;
		}
		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String originalFilename = "file2";
		String renameFilename = "file1";
		Map<String
				originalContent);
		Map<String
				slightlyModifiedContent);
		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename);

		String failingPath = null;
		if (dirtyOriginal) {
			writeTrashFile(originalFilename
			git.add().addFilepattern(originalFilename).call();
			failingPath = originalFilename;
		}
		if (dirtyRename) {
			writeTrashFile(renameFilename
			git.add().addFilepattern(renameFilename).call();
			failingPath = renameFilename;
		}

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(mergeResult.getFailingPaths()
				Map.of(failingPath
		if (dirtyOriginal) {
			assertEquals(read(originalFilename)
		}
		if (dirtyRename) {
			assertEquals(read(renameFilename)
		}
	}

	@Theory
	public void checkRenameModify_dirtyWorkingTree_renameInTheirs_fail(
			MergeStrategy strategy
			boolean dirtyOriginal
		if (!dirtyOriginal && !dirtyRename) {
			return;
		}
		if (isRenameInOurs && dirtyOriginal && !dirtyRename) {
			return;
		}
		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String originalFilename = "file2";
		String renameFilename = "file1";
		Map<String
				originalContent);
		Map<String
				slightlyModifiedContent);
		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename);

		String failingPath = null;
		if (dirtyOriginal) {
			writeTrashFile(originalFilename
			failingPath = originalFilename;
		}
		if (dirtyRename) {
			writeTrashFile(renameFilename
			failingPath = renameFilename;
		}

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(mergeResult.getFailingPaths()
				Map.of(failingPath
		if (dirtyOriginal) {
			assertEquals(read(originalFilename)
		}
		if (dirtyRename) {
			assertEquals(read(renameFilename)
		}
	}

	@Theory
	public void checkRenameModify_dirtyWorkingTree_renameInOurs_merged(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String originalFilename = "file2";
		String renameFilename = "file1";
		Map<String
				originalContent);
		Map<String
				slightlyModifiedContent);
		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				filesWithModify);

		writeTrashFile(originalFilename

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(read(originalFilename)
		assertEquals(read(renameFilename)
	}

	@Theory
	public void checkSourceAddedOnRenameSide_dirtyWorkTree_failed(
			MergeStrategy strategy
		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String originalFilename = "file2";
		String renameFilename = "file1";
		Map<String
				originalContent
		Map<String
				slightlyModifiedContent);
		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename);

		writeTrashFile(originalFilename

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(mergeResult.getFailingPaths()
				Map.of(originalFilename
		assertEquals(read(originalFilename)
	}

	@Theory
	public void checkSourceAddedOnRenameSide_dirtyIndex_failed(
			MergeStrategy strategy
		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		String originalFilename = "file2";
		String renameFilename = "file1";
		Map<String
				originalContent
		Map<String
				slightlyModifiedContent);
		RevCommit mergeCommit = setUpMerge(git
				Map.of(originalFilename
				isRenameInOurs ? filesWithRename : filesWithModify
				isRenameInOurs ? filesWithModify : filesWithRename);

		writeTrashFile(originalFilename
		git.add().addFilepattern(originalFilename).call();

		MergeResult mergeResult = git.merge().include(mergeCommit)
				.setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(mergeResult.getFailingPaths()
				Map.of(originalFilename
		assertEquals(read(originalFilename)
	}


	@DataPoints("fileAdditions")
	public static final List<String> fileAdditionsData = List.of("file1.1"
			"file3"

	@DataPoints("singleRenamePairs")
	public static final List<Entry<String
			.of(
					Map.entry("test/file2"
					Map.entry("test/file1"
					Map.entry("test/a/file1"
					Map.entry("test/z/file1"
					Map.entry("test/file1"
					Map.entry("test/file1"
					Map.entry("test/file1"
					Map.entry("test/file1"
					Map.entry("test/w/file1"
					Map.entry("test/a/file1"
					Map.entry("test/w/file1"
					Map.entry("test/a/file1"

	@Theory
	public void checkRenameModify_withFilesInSameDir_merged(
			MergeStrategy strategy
			@FromDataPoints("singleRenamePairs") Entry<String
			@FromDataPoints("fileAdditions") String fileAddition
			boolean isRenameInOurs) throws Exception {
		String originalContent = "a\nb\nc";
		Map<String
		originalFiles.put(renamePair.getKey()
		Map<String
		nonRenameFiles.put(renamePair.getKey()
		String addedFile = getDir(renamePair.getKey()) + "/" + fileAddition;
		nonRenameFiles.put(addedFile
		Map<String
		filesWithRename.put(renamePair.getValue()
		Map<String

		expectedFiles.put(renamePair.getValue()
		expectedFiles.put(addedFile
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : nonRenameFiles
				isRenameInOurs ? nonRenameFiles : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameMoveToParent_withFilesInSameDir_merged(
			MergeStrategy strategy
		String originalContent = "a\nb\nc";
		Map<String
		originalFiles.put("test/a/file1"
		Map<String
		nonRenameFiles.put("test/a/file1"
		nonRenameFiles.put("test/a/file1.1"
		Map<String
		filesWithRename.put("test/file1"
		Map<String
		expectedFiles.put("test/file1"
		expectedFiles.put("test/a/file1.1"
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : nonRenameFiles
				isRenameInOurs ? nonRenameFiles : filesWithRename
				expectedFiles);
	}

	@Theory
	public void checkRenameToSubDir_withFilesInSameDir_merged(
			MergeStrategy strategy
		String originalContent = "a\nb\nc";
		Map<String
		originalFiles.put("test/file1"
		Map<String
		nonRenameFiles.put("test/file1"
		nonRenameFiles.put("test/file1.1"
		Map<String
		filesWithRename.put("test/a/file1"
		Map<String
		expectedFiles.put("test/a/file1"
		expectedFiles.put("test/file1.1"
		testRename_merged(strategy
				isRenameInOurs ? filesWithRename : nonRenameFiles
				isRenameInOurs ? nonRenameFiles : filesWithRename
				expectedFiles);

	}

	@Theory
	public void checkRenameDir_allFilesMoved_someFilesAddedOnRenameSide(
			MergeStrategy strategy
			@FromDataPoints("renameLists") List<Entry<String
			boolean isRenameInOurs) throws Exception {
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		Map<String
		for (Entry<String
			originalFiles.put(renamePair.getKey()
					originalContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			noRenameFiles.put(renamePair.getKey()
					slightlyModifiedContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			renameFiles.put(renamePair.getValue()
					originalContent + renamePair.getKey());
		}
		String renameDir = getDir(renamePairs.get(0).getValue());
		for (String fileAddition : fileAdditionsData) {
			renameFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		Map<String
		for (Entry<String
			expectedFiles.put(renamePair.getValue()
					slightlyModifiedContent + renamePair.getKey());
		}
		for (String fileAddition : fileAdditionsData) {
			expectedFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		testRename_merged(strategy
				isRenameInOurs ? renameFiles : noRenameFiles
				isRenameInOurs ? noRenameFiles : renameFiles
	}

	@Theory
	public void checkRenameDir_allFilesMoved_someFilesAddedOnModifySide(
			MergeStrategy strategy
			@FromDataPoints("renameLists") List<Entry<String
			boolean isRenameInOurs) throws Exception {
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		Map<String
		for (Entry<String
			originalFiles.put(renamePair.getKey()
					originalContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			noRenameFiles.put(renamePair.getKey()
					slightlyModifiedContent + renamePair.getKey());
		}
		String originalDir = getDir(renamePairs.get(0).getKey());
		for (String fileAddition : fileAdditionsData) {
			noRenameFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		Map<String
		for (Entry<String
			renameFiles.put(renamePair.getValue()
					originalContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			expectedFiles.put(renamePair.getValue()
					slightlyModifiedContent + renamePair.getKey());
		}
		for (String fileAddition : fileAdditionsData) {
			expectedFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		testRename_merged(strategy
				isRenameInOurs ? renameFiles : noRenameFiles
				isRenameInOurs ? noRenameFiles : renameFiles
	}

	@Theory
	public void checkRenameDir_allFilesMoved_someFilesAddedOnBothSides(
			MergeStrategy strategy
			@FromDataPoints("renameLists") List<Entry<String
			boolean isRenameInOurs) throws Exception {
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		Map<String
		for (Entry<String
			originalFiles.put(renamePair.getKey()
					originalContent + renamePair.getKey());
		}
		Map<String
		for (Entry<String
			noRenameFiles.put(renamePair.getKey()
					slightlyModifiedContent + renamePair.getKey());
		}
		String originalDir = getDir(renamePairs.get(0).getKey());
		for (String fileAddition : fileAdditionsData) {
			noRenameFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		Map<String
		for (Entry<String
			renameFiles.put(renamePair.getValue()
					originalContent + renamePair.getKey());
		}
		String renameDir = getDir(renamePairs.get(0).getValue());
		for (String fileAddition : fileAdditionsData) {
			renameFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		Map<String
		for (Entry<String
			expectedFiles.put(renamePair.getValue()
					slightlyModifiedContent + renamePair.getKey());
		}
		for (String fileAddition : fileAdditionsData) {
			expectedFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		for (String fileAddition : fileAdditionsData) {
			expectedFiles.put(String.format("%s/%s"
					String.format("Another %s file was added in thiers"
							fileAddition));
		}
		testRename_merged(strategy
				isRenameInOurs ? renameFiles : noRenameFiles
				isRenameInOurs ? noRenameFiles : renameFiles
	}

	private String getDir(String path) {
		int endDir = path.lastIndexOf("/");
		return path.substring(0
	}

}
