package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class StashApplyCommandTest extends RepositoryTestCase {
	private Git git;

	private PersonIdent author = new PersonIdent("Abhishek Bhatnagar"
			"abhatnag@redhat.com");

	private PersonIdent committer = new PersonIdent("Abhishek Bhatnagar"
			"abhatnag@redhat.com");

	private String commitMessage = "Saved working directory and index state WIP on master: 372b87c test\nHEAD is now at 372b87c test\n";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("File1.txt"

		git.add().addFilepattern("File1.txt").call();
		git.commit().setMessage("Test file commit").call();
	}

	@Test
	public void testStash() throws Exception {
		System.out.println("==First Read==");
		readFile("Stash Created"
				+ "/File1.txt");

		System.out.println("==Edit and then Read==");
		editFile("edited content to be stashed"
				.getWorkTree() + "/File1.txt");
		readFile("File Edited"
				+ "/File1.txt");

		System.out.println("==Create Stash==");
		git.stashCreate().setAuthor(author).setCommitter(committer)
				.setMessage(commitMessage).call();
		readFile("Stash Created"
				+ "/File1.txt");

		System.out.println("==List Stash==");
		git.stashList().call();
		System.out.println("==Apply Stash==");
		git.stashApply().call();

		readFile("Stash Applied"
				+ "/File1.txt");
	}

	public void readFile(String event
		try {
			FileReader input = new FileReader(fileName);
			BufferedReader bufRead = new BufferedReader(input);

			String line;
			int count = 0;

			line = bufRead.readLine();
			count++;

			while (line != null) {
				System.out.println(event + ": " + line);
				line = bufRead.readLine();
				count++;
			}

			bufRead.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getStackTrace());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editFile(String mssg
		try {
			FileWriter output = new FileWriter(fileName);
			BufferedWriter bufWrite = new BufferedWriter(output);

			output.write(mssg);

			bufWrite.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: java ReadFile filename\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
