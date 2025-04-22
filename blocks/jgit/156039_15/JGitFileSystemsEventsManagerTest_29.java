package org.eclipse.jgit.niofs.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JGitFileSystemsEventsManagerTest {

	JGitFileSystemsEventsManager manager;
	JGitEventsBroadcast jGitEventsBroadcastMock = mock(JGitEventsBroadcast.class);
	ClusterMessageService clusterMessageService = new ClusterMessageService() {

		@Override
		public void connect() {

		}

		@Override
		public <T> void createConsumer(ClusterMessageService.DestinationType type
				Consumer<T> listener) {

		}

		@Override
		public void broadcast(ClusterMessageService.DestinationType type

		}

		@Override
		public boolean isSystemClustered() {
			return true;
		}

		@Override
		public void close() {

		}
	};

	@Before
	public void setup() {
		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}

			@Override
			ClusterMessageService getClusterMessageService() {
				return clusterMessageService;
			}

			@Override
			JGitFileSystemWatchServices createFSWatchServicesManager() {
				return mock(JGitFileSystemWatchServices.class);
			}
		};
	}

	@Test
	public void doNotSetupClusterTest() {
		JGitFileSystemsEventsManager another = new JGitFileSystemsEventsManager();
		assertNull(another.getjGitEventsBroadcast());
	}

	@Test
	public void setupClusterTest() {
		assertNotNull(manager.getjGitEventsBroadcast());
	}

	@Test
	public void createWatchService() throws IOException {
		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}

			ClusterMessageService getClusterMessageService() {
				return clusterMessageService;
			}
		};

		WatchService fs = manager.newWatchService("fs");

		assertNotNull(fs);
		assertTrue(manager.getFsWatchServices().containsKey("fs"));
		verify(jGitEventsBroadcastMock).createWatchService("fs");
	}

	@Test
	public void shouldNotPublishEventsForANotWatchedFS() throws IOException {
		WatchService fsDora = manager.newWatchService("fsDora");
		WatchService fsBento = manager.newWatchService("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class)

		manager.publishEvents("another"

		verify(jGitEventsBroadcastMock
	}

	@Test
	public void publishEventsShouldBeWatched() throws IOException {
		WatchService fsDoraWS = manager.newWatchService("fsDora");
		WatchService fsBento = manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class)

		manager.publishEvents("fsDora"

		verify(fsDoraWServices).publishEvents(any()
		verify(jGitEventsBroadcastMock).broadcast(eq("fsDora")
		verify(fsBentoWServices
	}

	@Test
	public void publishEventsWithoutBroadcast() throws IOException {
		manager.newWatchService("fsDora");
		manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class)

		manager.publishEvents("fsDora"

		verify(fsDoraWServices).publishEvents(any()
		verify(jGitEventsBroadcastMock
		verify(fsBentoWServices
	}

	@Test
	public void watchServicesEvents() throws IOException {

		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}
		};

		WatchService fsDora1 = manager.newWatchService("fsDora");
		WatchService fsDora2 = manager.newWatchService("fsDora");

		List<WatchEvent<?>> list3events = Arrays.asList(mock(WatchEvent.class)
				mock(WatchEvent.class));

		List<WatchEvent<?>> list2events = Arrays.asList(mock(WatchEvent.class)

		manager.publishEvents("fsDora"

		List<WatchEvent<?>> watchEvents = fsDora1.poll().pollEvents();
		assertEquals(3
		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(3

		manager.publishEvents("fsDora"
		manager.publishEvents("fsDora"

		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(3

		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(2

		watchEvents = fsDora1.poll().pollEvents();
		assertEquals(3

		watchEvents = fsDora1.poll().pollEvents();
		assertEquals(2
	}

	@Test
	public void closeTest() throws IOException {
		manager.newWatchService("fsDora");
		manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		manager.close("fsDora");

		verify(fsDoraWServices).close();
		verify(fsBentoWServices
	}

	@Test
	public void testShutdown() throws IOException {
		manager.newWatchService("fsPetra");
		manager.newWatchService("fsEureka");

		JGitFileSystemWatchServices fsPetraWatchService = manager.getFsWatchServices().get("fsPetra");
		JGitFileSystemWatchServices fsEurekaWatchService = manager.getFsWatchServices().get("fsEureka");

		manager.shutdown();

		verify(fsPetraWatchService).close();
		verify(fsEurekaWatchService).close();
		verify(jGitEventsBroadcastMock).close();
	}
}
