package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_MISSING_OBJECT;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE_NONFASTFORWARD;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
