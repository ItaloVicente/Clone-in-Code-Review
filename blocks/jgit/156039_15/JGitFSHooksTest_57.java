package org.eclipse.jgit.niofs.internal.op.extensions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.assertj.core.api.Assertions;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooksConstants;
import org.eclipse.jgit.niofs.internal.hook.JGitFSHooks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JGitFSHooksTest {

	private static final String FS_NAME = "dora";
	private static final Integer EXIT_CODE = 0;

	@Captor
	private ArgumentCaptor<FileSystemHookExecutionContext> contextArgumentCaptor;

	@Test
	public void executeFSHooksTest() {

		FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(FS_NAME);

		testExecuteFSHooks(ctx

		ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE

		testExecuteFSHooks(ctx
	}

	private void testExecuteFSHooks(FileSystemHookExecutionContext ctx
		AtomicBoolean executedWithLambda = new AtomicBoolean(false);

		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
			@Override
			public void execute(FileSystemHookExecutionContext context) {
				assertEquals(FS_NAME
			}
		});

		FileSystemHooks.FileSystemHook lambdaHook = context -> {
			assertEquals(FS_NAME
			executedWithLambda.set(true);
		};

		JGitFSHooks.executeFSHooks(hook
		JGitFSHooks.executeFSHooks(lambdaHook

		verifyFSHook(hook

		assertTrue(executedWithLambda.get());
	}

	@Test
	public void executeFSHooksArrayTest() {

		FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(FS_NAME);

		testExecuteFSHooksArray(ctx

		ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE

		testExecuteFSHooksArray(ctx
	}

	private void testExecuteFSHooksArray(FileSystemHookExecutionContext ctx

		AtomicBoolean executedWithLambda = new AtomicBoolean(false);

		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
			@Override
			public void execute(FileSystemHookExecutionContext context) {
				assertEquals(FS_NAME
			}
		});

		FileSystemHooks.FileSystemHook lambdaHook = context -> {
			assertEquals(FS_NAME
			executedWithLambda.set(true);
		};

		JGitFSHooks.executeFSHooks(Arrays.asList(hook

		verifyFSHook(hook

		assertTrue(executedWithLambda.get());
	}

	private void verifyFSHook(FileSystemHooks.FileSystemHook hook
		verify(hook).execute(contextArgumentCaptor.capture());

		FileSystemHookExecutionContext ctx = contextArgumentCaptor.getValue();

		Assertions.assertThat(ctx).isNotNull().hasFieldOrPropertyWithValue("fsName"

		if (hookType.equals(FileSystemHooks.PostCommit)) {
			Assertions.assertThat(ctx.getParamValue(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE)).isNotNull()
					.isEqualTo(EXIT_CODE);
		}
	}
}
