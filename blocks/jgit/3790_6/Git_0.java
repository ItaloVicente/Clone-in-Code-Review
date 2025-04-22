package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class StashApplyCommandTest extends RepositoryTestCase {
	private Git git;

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
		git.stashCreate().call();
		readFile("Stash Created"
				+ "/File1.txt");

		git.stashList().call();
		git.stashApply().call();

		readFile("Stash Created"
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

	public void editFile(String fileName
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
