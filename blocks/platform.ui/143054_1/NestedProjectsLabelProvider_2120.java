import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.internal.resources.MarkerManager;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
