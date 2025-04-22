package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
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
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
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
      MergeStrategy.RECURSIVE

  @Before
  public void enableRename() throws IOException
    StoredConfig config = db.getConfig();
    config.setString(ConfigConstants.CONFIG_DIFF_SECTION
    config.save();
  }


  private AbstractTreeIterator getTreeIterator(String name) throws IOException {
    final ObjectId id = db.resolve(name);
    if (id == null)
      throw new IllegalArgumentException(name);
    final CanonicalTreeParser p = new CanonicalTreeParser();
    try (ObjectReader or = db.newObjectReader(); RevWalk rw = new RevWalk(db)) {
      p.reset(or
      return p;
    }
  }

  public void testRename_merged(MergeStrategy strategy

    Map<String
    originalFiles.put(originalName
    Map<String
    oursFiles.put(oursName
    Map<String
    theirsFiles.put(theirsName
    testRename_merged(strategy
  }

  public void testRename_merged(MergeStrategy strategy
    if (!strategy.equals(MergeStrategy.RECURSIVE)) {
      return;
    }
    MergeResult mergeResult = mergeRename(strategy
    assertEquals(mergeResult.getMergeStatus()
    Set<String> expectedIndexContent = new HashSet<>();
    for (Entry<String
      assertEquals(expectedFile.getValue()
      expectedIndexContent.add(String.format("%s
          expectedFile.getValue()));
    }
    Set<String> stagedFiles = Arrays.asList(indexState(CONTENT).split("\\[|\\]")).stream()
        .filter(s ->
            !Strings.isNullOrEmpty(s)).collect(Collectors.toSet());
    assertEquals(stagedFiles
  }

  public void testRename_withConflict(MergeStrategy strategy
    if (!strategy.equals(MergeStrategy.RECURSIVE)) {
      return;
    }
    MergeResult mergeResult = mergeRename(strategy
    assertEquals(mergeResult.getMergeStatus()
    for (Entry<String
      assertEquals(expectedFile.getValue()
    }
    Set<String> stagedFiles = Arrays.asList(indexState(CONTENT).split("\\[|\\]")).stream()
        .filter(s ->
            !Strings.isNullOrEmpty(s)).collect(Collectors.toSet());
    assertEquals(stagedFiles
  }

  private MergeResult mergeRename(MergeStrategy strategy
    Git git = Git.wrap(db);
    for (Entry<String
      writeTrashFile(originalFile.getKey()
      git.add().addFilepattern(originalFile.getKey()).call();
    }
    RevCommit commitI = git.commit().setMessage("Initial commit").call();

    git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("second-branch").call();
    for (Entry<String
      git.rm().addFilepattern(originalFile.getKey()).call();
    }
    for (Entry<String
      writeTrashFile(theirsFile.getKey()
      git.add().addFilepattern(theirsFile.getKey()).call();
    }
    RevCommit renameCommit = git.commit().setMessage("Rename file").call();

    git.checkout().setName("master").call();

    for (Entry<String
      git.rm().addFilepattern(originalFile.getKey()).call();
    }
    for (Entry<String
      writeTrashFile(oursFile.getKey()
      git.add().addFilepattern(oursFile.getKey()).call();
    }

    RevCommit modifyContentCommit = git.commit().setMessage("Commit slightly modified content")
        .call();

    return git.merge().include(renameCommit).setStrategy(strategy).call();
  }

  public void checkRenameTo_modifyConflict(MergeStrategy strategy
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    Map<String
    for (Entry<String
      originalFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      noRenameFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      renameFiles.put(renamePair.getValue()
    }
    String renameDir = getDir(renamePairs.get(0).getValue());
    Map<String
    for (Entry<String
      expectedFiles.put(renamePair.getValue()
    }
    testRename_merged(strategy
        isRenameInOurs ? noRenameFiles : renameFiles
  }

  @DataPoints("singleRenamePairs")
  public static final List<Entry<String
      Map.entry("test/file2"
      Map.entry("test/file1"
      Map.entry("test/a/file1"
      Map.entry("test/z/file1"
      Map.entry("test/file1"
      Map.entry("test/file1"
      Map.entry("test/w/file1"
      Map.entry("test/a/file1"

  @DataPoints("renameLists")
  public static final List<List<Entry<String
      List.of(Map.entry("test/file2"
      List.of(Map.entry("test/file1"
      List.of(Map.entry("test/a/file1"
      List.of(Map.entry("test/z/file1"
      List.of(Map.entry("test/file1"
      List.of(Map.entry("test/file1"
      List.of(Map.entry("test/w/file1"
       List.of(Map.entry("test/a/file1"
  );

  @DataPoints("renameListsSplit")
  public static final List<List<Entry<String
      List.of(Map.entry("test/a/file1"
      List.of(Map.entry("test/a/file1"
  );
  @DataPoints("fileAdditions")
  public static final List<String> fileAdditionsData = List.of("file1.1"

  @Theory
  public void checkRenameFile_modifyConflict_renameInOurs(MergeStrategy strategy
    checkRenameTo_modifyConflict(strategy

  }

  @Theory
  public void checkRenameFile_modifyConflict_renameInTheirs(MergeStrategy strategy
    checkRenameTo_modifyConflict(strategy
  }

  @Theory
  public void checkRenameSplitDir_modifyConflict_renameInOurs(MergeStrategy strategy
    checkRenameTo_modifyConflict(strategy

  }

  @Theory
  public void checkRenameSplitDir_modifyConflict_renameInTheirs(MergeStrategy strategy
    checkRenameTo_modifyConflict(strategy
  }

  @Theory
  public void checkRenameFile_noContentModification(MergeStrategy strategy
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
  }

  @Theory
  public void checkRenameMoveToParent_noContentModification(MergeStrategy strategy
    String originalContent = "a\nb\nc";
    Map<String
    originalFiles.put("test/sub/file1"
    Map<String
    nonRenameFiles.put("test/sub/file1"
    nonRenameFiles.put("test/sub/file1.1"
    Map<String
    filesWithRename.put("test/file1"
    Map<String
    expectedFiles.put("test/file1"
    expectedFiles.put("test/sub/file1.1"
    testRename_merged(strategy
        isRenameInOurs ? filesWithRename : nonRenameFiles
        isRenameInOurs ? nonRenameFiles : filesWithRename
  }

  @Theory
  public void checkRenameToSubDir_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalFilename = "test/file1";
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    String oursFilename = originalFilename;
    String oursContent = slightlyModifiedContent;
    String theirsFilename = "test/sub/file1";
    String theirsContent = originalContent;

    Map<String
    expectedFiles.put(theirsFilename
    testRename_merged(strategy
        oursContent

  }

  @Theory
  public void checkRenameMoveToParent_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalFilename = "test/sub/file1";
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    String oursFilename = originalFilename;
    String oursContent = slightlyModifiedContent;
    String theirsFilename = "test/file2";
    String theirsContent = originalContent;

    Map<String
    expectedFiles.put("test/file2"
    Map<String
    originalFiles.put(originalFilename
    Map<String
    oursFiles.put(oursFilename
    Map<String
    theirsFiles.put(theirsFilename
    testRename_merged(strategy

  }

  @Theory
  public void checkRenameOnBothSides_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalFilename = "test/file1";
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    String oursFilename = "test/file2";
    String oursContent = slightlyModifiedContent;
    String theirsFilename = "test/file3";
    String theirsContent = originalContent;

    Map<String
    expectedFiles.put("test/file2"
    Map<String
    originalFiles.put(originalFilename
    Map<String
    oursFiles.put(oursFilename
    Map<String
    theirsFiles.put(theirsFilename
    testRename_withConflict(strategy

  }

  @Theory
  public void checkRenameDir_AllFilesMoved_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalContent1 = "a\nb\nc";
    String originalContent2 = "x\ny\nz";
    String slightlyModifiedContent1 = "a\nb\nb";
    String slightlyModifiedContent2 = "x\nz\nz";
    Map<String
    originalFiles.put("test/a/file1"
    originalFiles.put("test/a/file2"
    Map<String
    oursFiles.put("test/a/file1"
    oursFiles.put("test/a/file2"
    Map<String
    theirsFiles.put("test/sub/file1"
    theirsFiles.put("test/sub/file2"
    Map<String
    expectedFiles.put("test/sub/file1"
    expectedFiles.put("test/sub/file2"
    testRename_merged(strategy

  }

  @Theory
  public void checkRenameSubDir_AllFilesMoved_SomeFilesAddedOnRenameSide_modifyConflict(MergeStrategy strategy
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    Map<String
    for (Entry<String
      originalFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      noRenameFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      renameFiles.put(renamePair.getValue()
    }
    String renameDir = getDir(renamePairs.get(0).getValue());
    for (String fileAddition : fileAdditionsData) {
      renameFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    Map<String
    for (Entry<String
      expectedFiles.put(renamePair.getValue()
    }
    for (String fileAddition : fileAdditionsData) {
      expectedFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    testRename_merged(strategy
        isRenameInOurs ? noRenameFiles : renameFiles
  }

  @Theory
  public void checkRenameSubDir_AllFilesMoved_SomeFilesAddedOnModifySide_modifyConflict(MergeStrategy strategy
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    Map<String
    for (Entry<String
      originalFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      noRenameFiles.put(renamePair.getKey()
    }
    String originalDir = getDir(renamePairs.get(0).getKey());
    for (String fileAddition : fileAdditionsData) {
      noRenameFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    Map<String
    for (Entry<String
      renameFiles.put(renamePair.getValue()
    }
    Map<String
    for (Entry<String
      expectedFiles.put(renamePair.getValue()
    }
    for (String fileAddition : fileAdditionsData) {
      expectedFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    testRename_merged(strategy
        isRenameInOurs ? noRenameFiles : renameFiles
  }


  @Theory
  public void checkRenameSubDir_AllFilesMoved_SomeFilesAddedOnBothSides_modifyConflict(MergeStrategy strategy
    String originalContent = "a\nb\nc";
    String slightlyModifiedContent = "a\nb\nb";
    Map<String
    for (Entry<String
      originalFiles.put(renamePair.getKey()
    }
    Map<String
    for (Entry<String
      noRenameFiles.put(renamePair.getKey()
    }
    String originalDir = getDir(renamePairs.get(0).getKey());
    for (String fileAddition : fileAdditionsData) {
      noRenameFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    Map<String
    for (Entry<String
      renameFiles.put(renamePair.getValue()
    }
    String renameDir = getDir(renamePairs.get(0).getValue());
    for (String fileAddition : fileAdditionsData) {
      renameFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    Map<String
    for (Entry<String
      expectedFiles.put(renamePair.getValue()
    }
    for (String fileAddition : fileAdditionsData) {
      expectedFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    for (String fileAddition : fileAdditionsData) {
      expectedFiles.put(String.format("%s/%s"
          String.format("Another %s file was added in thiers"
    }
    testRename_merged(strategy
        isRenameInOurs ? noRenameFiles : renameFiles
  }

  @Theory
  public void checkRenameSubDir_AllFilesMoved_SomeFilesAddedInOurs_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalContent1 = "a\nb\nc";
    String originalContent2 = "x\ny\nz";
    String slightlyModifiedContent1 = "a\nb\nb";
    String slightlyModifiedContent2 = "x\nz\nz";
    Map<String
    originalFiles.put("test/a/file1"
    originalFiles.put("test/a/file2"
    Map<String
    oursFiles.put("test/a/file1"
    oursFiles.put("test/a/file2"
    oursFiles.put("test/a/file3"
    Map<String
    theirsFiles.put("test/sub/file1"
    theirsFiles.put("test/sub/file2"
    Map<String
    expectedFiles.put("test/sub/file1"
    expectedFiles.put("test/sub/file2"
    expectedFiles.put("test/a/file3"
    testRename_merged(strategy
  }

  @Theory
  public void checkRenameSubDir_AllFilesMoved_SomeFilesAddedInBoth_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalContent1 = "a\nb\nc";
    String originalContent2 = "x\ny\nz";
    String slightlyModifiedContent1 = "a\nb\nb";
    String slightlyModifiedContent2 = "x\nz\nz";
    Map<String
    originalFiles.put("test/a/file1"
    originalFiles.put("test/a/file2"
    Map<String
    oursFiles.put("test/a/file1"
    oursFiles.put("test/a/file2"
    oursFiles.put("test/a/file1.1"
    Map<String
    theirsFiles.put("test/sub/file1"
    theirsFiles.put("test/sub/file2"
    theirsFiles.put("test/sub/file3"
    Map<String
    expectedFiles.put("test/sub/file1"
    expectedFiles.put("test/sub/file2"
    expectedFiles.put("test/a/file1.1"
    expectedFiles.put("test/sub/file3"
    testRename_merged(strategy
  }

  @Theory
  public void checkRename_dirSplit_modifyConflict(MergeStrategy strategy
    String originalContent1 = "a\nb\nc";
    String originalContent2 = "x\ny\nz";
    String slightlyModifiedContent1 = "a\nb\nb";
    String slightlyModifiedContent2 = "x\nz\nz";
    Map<String
    originalFiles.put("test/a/file1"
    originalFiles.put("test/a/file2"
    Map<String
    noRenameFiles.put("test/a/file1"
    noRenameFiles.put("test/a/file2"
    Map<String
    renameFiles.put("test/a1/file1"
    renameFiles.put("test/a2/file2"
    Map<String
    expectedFiles.put("test/a1/file1"
    expectedFiles.put("test/a2/file2"
    testRename_merged(strategy
        isRenameInOurs ? noRenameFiles : renameFiles
  }

  @Theory
  public void checkRename_subDirSplit_modifyConflict(MergeStrategy strategy) throws Exception {
    String originalContent1 = "a\nb\nc";
    String originalContent2 = "x\ny\nz";
    String slightlyModifiedContent1 = "a\nb\nb";
    String slightlyModifiedContent2 = "x\nz\nz";
    Map<String
    originalFiles.put("test/file1"
    originalFiles.put("test/file2"
    Map<String
    oursFiles.put("test/file1"
    oursFiles.put("test/file2"
    Map<String
    theirsFiles.put("test/a1/file1"
    theirsFiles.put("test/a2/file2"
    Map<String
    expectedFiles.put("test/a1/file1"
    expectedFiles.put("test/a2/file2"
    testRename_merged(strategy

  }

  private String getDir(String path){
    int endDir = path.lastIndexOf("/");
    return path.substring(0
  }

}
