package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.reftable.ReftableBatchRefUpdate;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableDatabase;
import org.eclipse.jgit.internal.storage.reftable.ReftableStack;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter.Stats;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.eclipse.jgit.lib.Ref.UNDEFINED_UPDATE_INDEX;

public class FileReftableDatabase extends RefDatabase {
    private final ReftableDatabase reftableDatabase;
    private final FileRepository fileRepository;
    private final File stackPath;

    FileReftableDatabase(FileRepository repo
        fileRepository = repo;
        stackPath = refstackName;
        reftableDatabase = new ReftableDatabase(() -> FileReftableStack.open(refstackName));
    }

    private FileReftableStack fileReftableStack() throws IOException {
        return (FileReftableStack) reftableDatabase.stack();
    };

    public ReflogReader getReflogReader(String refname) throws IOException {
        return reftableDatabase.getReflogReader(refname);
    }


    public static boolean isReftable(File repoDir) {
        return new File(repoDir
                new File(repoDir
    }
    ReentrantLock getLock() { return reftableDatabase.getLock(); }

    @Override
    public boolean performsAtomicTransactions() {
        return true;
    }

    @NonNull
    @Override
    public BatchRefUpdate newBatchUpdate() {
        return new FileReftableBatchRefUpdate(this
    }

    @Override
    public RefUpdate newUpdate(String refName
            throws IOException {
        boolean detachingSymbolicRef = false;
        Ref ref = exactRef(refName);

        if (ref == null)
            ref = new ObjectIdRef.Unpeeled(NEW
        else
            detachingSymbolicRef = detach && ref.isSymbolic();

        RefUpdate update = new FileReftableRefUpdate(fileRepository
        if (detachingSymbolicRef)
            update.setDetachingSymbolicRef();
        return update;
    }

    @Override
    public Ref exactRef(String name) throws IOException {
        return reftableDatabase.exactRef(name);
    }

    @Override
    public List<Ref> getRefs() throws IOException {
        return super.getRefs();
    }

    @Override
    public Map<String
        return reftableDatabase.getRefs(prefix);
    }

    @Override
    public List<Ref> getAdditionalRefs() throws IOException {
        return null;
    }

    @Override
    public Ref peel(Ref ref) throws IOException {
        Ref oldLeaf = ref.getLeaf();
        if (oldLeaf.isPeeled() || oldLeaf.getObjectId() == null) {
            return ref;
        }
        return recreate(ref

    }
    Ref doPeel(Ref leaf) throws MissingObjectException
            IOException {
        try (RevWalk rw = new RevWalk(fileRepository)) {
            RevObject obj = rw.parseAny(leaf.getObjectId());
            if (obj instanceof RevTag) {
                return new ObjectIdRef.PeeledTag(
                        leaf.getStorage()
                        leaf.getName()
                        leaf.getObjectId()
                        rw.peel(obj).copy()
                        hasVersioning() ? leaf.getUpdateIndex()
                                : UNDEFINED_UPDATE_INDEX);
            } else {
                return new ObjectIdRef.PeeledNonTag(
                        leaf.getStorage()
                        leaf.getName()
                        leaf.getObjectId()
                        hasVersioning() ? leaf.getUpdateIndex()
                                : UNDEFINED_UPDATE_INDEX);
            }
        }
    }

    static Ref recreate(Ref old
        if (old.isSymbolic()) {
            Ref dst = recreate(old.getTarget()
            return new SymbolicRef(old.getName()
                    hasVersioning ? old.getUpdateIndex()
                            : UNDEFINED_UPDATE_INDEX);
        }
        return leaf;
    }

    private class FileRefRename extends RefRename {
        FileRefRename(RefUpdate src
            super(src
        }

        void writeRename(ReftableWriter w
            long idx = reftableDatabase.stack().nextUpdateIndex();
            w.setMinUpdateIndex(idx).setMaxUpdateIndex(idx)
                    .begin(os);
            List<Ref> refs = new ArrayList<>(3);

            Ref dest = destination.getRef();
            Ref head = exactRef("HEAD");
            if (head != null && head.isSymbolic() && head.getLeaf().getName().equals(source.getName())) {
                head = new SymbolicRef(
                        "HEAD"
                        dest
                        idx);
                refs.add(head);
            }

            ObjectId objId = source.getRef().getObjectId();

            refs.add(new ObjectIdRef.PeeledNonTag(
                            Ref.Storage.NEW
                            destination.getName()
                            objId));
            refs.add(new ObjectIdRef.Unpeeled(
                    Ref.Storage.NEW
                    source.getName()
                    null));

            w.sortAndWriteRefs(refs);
            PersonIdent who = destination.getRefLogIdent();
            if (who == null) {
                who = new PersonIdent(fileRepository);
            }

            if (!destination.getRefLogMessage().isEmpty()) {
                List<String> refnames =
                        refs.stream().map(r -> r.getName()).collect(Collectors.toList());
                Collections.sort(refnames);
                for (String s : refnames) {
                    ObjectId old = ("HEAD".equals(s) || s.equals(source.getName()))
                            ? objId : ObjectId.zeroId();
                    ObjectId newId = ("HEAD".equals(s) || s.equals(destination.getName()))
                            ? objId : ObjectId.zeroId();

                    w.writeLog(s
                }
            }
        }

        @Override
        protected RefUpdate.Result doRename() throws IOException {
            Ref src = exactRef(source.getName());
            if (src.isSymbolic()) {
                return RefUpdate.Result.IO_FAILURE;
            }

            if (exactRef(destination.getName()) != null || src == null
                    || !source.getOldObjectId().equals(src.getObjectId())) {
                return RefUpdate.Result.LOCK_FAILURE;
            }

            try {
                addReftableWithRetry(this::writeRename);
            } catch (RetryException e) {
                return RefUpdate.Result.LOCK_FAILURE;
            }

            return RefUpdate.Result.RENAMED;
        }
    }

    @Override
    public RefRename newRename(String fromName
            throws IOException {
        RefUpdate src = newUpdate(fromName
        RefUpdate dst = newUpdate(toName
        return new FileRefRename(src
    }


    @Override
    public boolean isNameConflicting(String name) {
        return false;
    }

    @Override
    public void close() {
        try {
            fileReftableStack().close();
        } catch (IOException e) {

        }
    }

    @Override
    public void create() throws IOException {
        FileUtils.mkdir(new File(fileRepository.getDirectory()
    }


    interface Writer {
        void call(ReftableWriter w
    }

    static class RetryException extends IOException {}

    void addReftableWithRetry(Writer w) throws IOException {
        int tried = 0;
        for (;;) {
            try {
                addReftable(w);
                return;
            } catch (RetryException e) {
                tried ++;
                if (tried > 2) {
                    throw e;
                }
            }
        }
    }

    void addReftable(Writer w) throws IOException {
        LockFile lock = new LockFile(stackPath);
        try {
            lock.lockForAppend();
            if (!fileReftableStack().uptodate()) {
                throw new RetryException();
            }

            String fn = filename(reftableDatabase.stack().nextUpdateIndex());

            File tmpTable = File.createTempFile(fn + "_"
                    stackPath.getParentFile());

            Stats stats;
            try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
                ReftableWriter rw = new ReftableWriter(reftableConfig());
                w.call(rw
                rw.finish();
                stats = rw.getStats();
            }

            fn += stats.refCount() > 0 ? ".ref" : ".log";
            File dest = new File(reftableDir()

            FileUtils.rename(tmpTable
                    StandardCopyOption.ATOMIC_MOVE);
            lock.write((fn + "\n").getBytes(UTF_8));
            if (!lock.commit()) {
                FileUtils.delete(dest);
                throw new RetryException();
            }

            reftableDatabase.clearCache();
        } finally {
            lock.unlock();
        }
    }

    private ReftableConfig reftableConfig() {
        return new ReftableConfig();
    }

    private class FileReftableBatchRefUpdate extends ReftableBatchRefUpdate {
        ReftableConfig config;
        ReftableStack stack;
        public FileReftableBatchRefUpdate(FileReftableDatabase db
            super(db
        }

        @Override
        protected ReftableStack getStack() throws IOException {
            return reftableDatabase.stack();
        }

        @Override
        protected void applyUpdates(List<Ref> newRefs
            try {
                addReftableWithRetry((rw
            } catch (RetryException e ) {
                for (ReceiveCommand c : pending) {
                    if (c.getResult() == ReceiveCommand.Result.NOT_ATTEMPTED) {
                        c.setResult(RefUpdate.Result.LOCK_FAILURE);
                    }
                }
            }
        }
    }

    private class FileReftableRefUpdate extends RefUpdate {
        FileRepository repo;

        FileReftableRefUpdate(FileRepository repo
            super(ref);
            this.repo = repo;
        }

        @Override
        protected RefDatabase getRefDatabase() {
            return FileReftableDatabase.this;
        }

        @Override
        protected Repository getRepository() {
            return repo;
        }

        @Override
        protected void unlock() {
        }

        private RevWalk rw;
        private Ref dstRef;

        @Override
        public Result update(RevWalk walk) throws IOException {
            try {
                rw = walk;
                return super.update(walk);
            } finally {
                rw = null;
            }
        }

        @Override
        protected boolean tryLock(boolean deref) throws IOException {
            dstRef = getRef();
            if (deref)
                dstRef = dstRef.getLeaf();

            Ref derefed = exactRef(dstRef.getName());
            if (derefed != null) {
                setOldObjectId(derefed.getObjectId());
            }

            return true;
        }

        void writeUpdate(ReftableWriter w
            Ref newRef= null;
            if (rw != null && !ObjectId.zeroId().equals(getNewObjectId())) {
                RevObject obj = rw.parseAny(getNewObjectId());
                if (obj instanceof RevTag) {
                    newRef = new ObjectIdRef.PeeledTag(
                            Ref.Storage.PACKED
                            dstRef.getName()
                            getNewObjectId()
                            rw.peel(obj).copy());
                }
            }
            if (newRef == null) {
                newRef = new ObjectIdRef.PeeledNonTag(
                        Ref.Storage.PACKED
                        dstRef.getName()
                        getNewObjectId());
            }


            long idx = reftableDatabase.stack().nextUpdateIndex();
            w.setMinUpdateIndex(idx).setMaxUpdateIndex(idx)
                    .begin(os).writeRef(newRef);

            ObjectId oldId = getOldObjectId();
            if (oldId == null) {
                oldId = ObjectId.zeroId();
            }
            w.writeLog(
                    dstRef.getName()
                    idx
                    getRefLogIdent()
                    oldId
                    getRefLogMessage());
        }

        @Override
        public PersonIdent getRefLogIdent() {
            PersonIdent who = super.getRefLogIdent();
            if (who == null) {
                who = new PersonIdent(getRepository());
            }
            return who;
        }

        void writeDelete(ReftableWriter w
            Ref newRef = new ObjectIdRef.Unpeeled(
                    Ref.Storage.NEW
                    dstRef.getName()
                    null);
            long idx = reftableDatabase.stack().nextUpdateIndex();
            w.setMinUpdateIndex(idx).setMaxUpdateIndex(idx)
                    .begin(os).writeRef(newRef);

            ObjectId oldId = ObjectId.zeroId();
            Ref old = exactRef(dstRef.getName());
            if (old != null) {
                old = old.getLeaf();
                if (old.getObjectId()!= null) {
                    oldId = old.getObjectId();
                }
            }

            w.writeLog(
                    dstRef.getName()
                    idx
                    getRefLogIdent()
                    oldId
                    ObjectId.zeroId()
                    getRefLogMessage());
        }

        @Override
        protected Result doUpdate(Result desiredResult) throws IOException {
            try {
                if (isRefLogIncludingResult()) {
                    setRefLogMessage(getRefLogMessage() + ": " + desiredResult.toString()
                            false);
                }

                addReftableWithRetry(this::writeUpdate);
            } catch (RetryException e) {
                return Result.LOCK_FAILURE;
            }
            return desiredResult;
        }

        @Override
        protected Result doDelete(Result desiredResult) throws IOException {
            try {
                if (isRefLogIncludingResult()) {
                    setRefLogMessage(getRefLogMessage() + ": " + desiredResult.toString()
                            false);
                }

                addReftableWithRetry(this::writeDelete);
            } catch (RetryException e) {
                return Result.LOCK_FAILURE;
            }
            return desiredResult;
        }


        void writeLink(ReftableWriter w
            long idx = reftableDatabase.stack().nextUpdateIndex();
            w.setMinUpdateIndex(idx).setMaxUpdateIndex(idx)
                    .begin(os).writeRef(dstRef);


            ObjectId beforeId = ObjectId.zeroId();
            Ref before = exactRef(dstRef.getName());
            if (before != null) {
                before = before.getLeaf();
                if (before.getObjectId()!= null) {
                    beforeId = before.getObjectId();
                }
            }

            Ref after = dstRef.getLeaf();
            ObjectId afterId = ObjectId.zeroId();
            if (after.getObjectId() != null) {
                afterId = after.getObjectId();
            }

            w.writeLog(
                    dstRef.getName()
                    idx
                    getRefLogIdent()
                    beforeId
                    getRefLogMessage());
        }

        @Override
        protected Result doLink(String target) throws IOException {
            try {
                if (isRefLogIncludingResult()) {
                    setRefLogMessage(getRefLogMessage() + ": " + Result.FORCED.toString()
                            false);
                }

                boolean exists = exactRef(getName()) != null;
                dstRef = new SymbolicRef(
                        getName()
                        new ObjectIdRef.Unpeeled(
                                Ref.Storage.NEW
                                target
                                null)
                        reftableDatabase.stack().nextUpdateIndex());

                addReftableWithRetry(this::writeLink);
                return exists ? Result.FORCED : Result.NEW;
            } catch (RetryException e) {
                return Result.LOCK_FAILURE;
            }
        }
    }

    private File reftableDir() {
        return new File(stackPath.getParentFile()
    }

    private String filename(long index) {
        return String.format("%06d"
    }

    static void writeConvertTable(Repository repo
        int size = 0;
        List<Ref> refs = repo.getRefDatabase().getRefs();
        for (Ref r : refs) {
            ReflogReader rlr = repo.getReflogReader(r.getName());
            size = Math.max(rlr.getReverseEntries().size()
        }

        w.setMinUpdateIndex(1).setMaxUpdateIndex(size + 1)
                .begin(os);

        try (RevWalk rw = new RevWalk(repo)) {
            List<Ref> toWrite = new ArrayList<>(refs.size());
            for (Ref r : refs) {
                toWrite.add(refForWrite(rw
            }

            w.sortAndWriteRefs(toWrite);
        }

        for (Ref r : refs) {
            long idx = size;
            ReflogReader reader = repo.getReflogReader(r.getName());
            for (ReflogEntry e : reader.getReverseEntries()) {
                w.writeLog(r.getName()
                idx--;
            }
        }
    }


    static Ref refForWrite(RevWalk rw
        if (r.isSymbolic()) {
            return new SymbolicRef(r.getName()
                    new ObjectIdRef.Unpeeled(NEW
        }
        ObjectId newId = r.getObjectId();
        RevObject obj = rw.parseAny(newId);
        RevObject peel = null;
        if (obj instanceof RevTag) {
            peel = rw.peel(obj);
        }
        if (peel != null) {
            return new ObjectIdRef.PeeledTag(PACKED
                    peel.copy());
        }
        return new ObjectIdRef.PeeledNonTag(PACKED
    }

    public static FileReftableDatabase convertFrom(FileRepository repo
        FileReftableDatabase newDb = null;
        try {
            newDb = new FileReftableDatabase(repo
            newDb.create();

            newDb.addReftable((rw
        } catch (Exception e) {
            newDb.stackPath.delete();
            throw e;
        }
        return newDb;
    }
}
