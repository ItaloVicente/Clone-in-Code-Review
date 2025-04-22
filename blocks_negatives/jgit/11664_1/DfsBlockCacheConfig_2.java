import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_READ_AHEAD_LIMIT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_READ_AHEAD_THREADS;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
