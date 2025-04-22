    public enum RequestPolicy {
        ADVERTISED

        REACHABLE_COMMIT

        TIP

        REACHABLE_COMMIT_TIP

        ANY;
    }

    public interface RequestValidator {
        void checkWants(UploadPack up
                throws PackProtocolException
    }

    @Deprecated
    public static class FirstLine {

        private final FirstWant firstWant;

        public FirstLine(String line) {
            try {
                firstWant = FirstWant.fromLine(line);
            } catch (PackProtocolException e) {
                throw new UncheckedIOException(e);
            }
        }

        public String getLine() {
            return firstWant.getLine();
        }

        public Set<String> getOptions() {
            if (firstWant.getAgent() != null) {
                Set<String> caps = new HashSet<>(firstWant.getCapabilities());
                caps.add(OPTION_AGENT + '=' + firstWant.getAgent());
                return caps;
            }
            return firstWant.getCapabilities();
        }
    }

    @FunctionalInterface
    private static interface IOConsumer<R> {
        void accept(R t) throws IOException;
    }

    private final Repository db;

    private final RevWalk walk;

    private PackConfig packConfig;

    private TransferConfig transferConfig;

    private int timeout;

    private boolean biDirectionalPipe = true;

    private InterruptTimer timer;

    private boolean clientRequestedV2;

    private InputStream rawIn;

    private ResponseBufferedOutputStream rawOut;

    private PacketLineIn pckIn;

    private OutputStream msgOut = NullOutputStream.INSTANCE;

    private ErrorWriter errOut = new PackProtocolErrorWriter();

    private Map<String

    private ProtocolV2Hook protocolV2Hook = ProtocolV2Hook.DEFAULT;

    private AdvertiseRefsHook advertiseRefsHook = AdvertiseRefsHook.DEFAULT;

    private boolean advertiseRefsHookCalled;

    private RefFilter refFilter = RefFilter.DEFAULT;

    private PreUploadHook preUploadHook = PreUploadHook.NULL;

    private PostUploadHook postUploadHook = PostUploadHook.NULL;

    String userAgent;

    private Set<ObjectId> wantIds = new HashSet<>();

    private final Set<RevObject> wantAll = new HashSet<>();

    private final Set<RevObject> commonBase = new HashSet<>();

    private int oldestTime;

    private Boolean okToGiveUp;

    private boolean sentReady;

    private Set<ObjectId> advertised;

    private final RevFlag WANT;

    private final RevFlag PEER_HAS;

    private final RevFlag COMMON;

    private final RevFlag SATISFIED;

    private final RevFlagSet SAVE;

    private RequestValidator requestValidator = new AdvertisedRequestValidator();

    private MultiAck multiAck = MultiAck.OFF;

    private boolean noDone;

    private PackStatistics statistics;

    private FetchRequest currentRequest;

    private CachedPackUriProvider cachedPackUriProvider;

    public UploadPack(Repository copyFrom) {
        db = copyFrom;
        walk = new RevWalk(db);
        walk.setRetainBody(false);

        walk.carry(PEER_HAS);

        SAVE = new RevFlagSet();
        SAVE.add(WANT);
        SAVE.add(PEER_HAS);
        SAVE.add(COMMON);
        SAVE.add(SATISFIED);

        setTransferConfig(null);
    }

    public final Repository getRepository() {
        return db;
    }

    public final RevWalk getRevWalk() {
        return walk;
    }

    public final Map<String
        return refs;
    }

    public void setAdvertisedRefs(@Nullable Map<String
        if (allRefs != null)
            refs = allRefs;
        else
            refs = db.getAllRefs();
        if (refFilter == RefFilter.DEFAULT)
            refs = transferConfig.getRefFilter().filter(refs);
        else
            refs = refFilter.filter(refs);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int seconds) {
        timeout = seconds;
    }

    public boolean isBiDirectionalPipe() {
        return biDirectionalPipe;
    }

    public void setBiDirectionalPipe(boolean twoWay) {
        biDirectionalPipe = twoWay;
    }

    public RequestPolicy getRequestPolicy() {
        if (requestValidator instanceof AdvertisedRequestValidator)
            return RequestPolicy.ADVERTISED;
        if (requestValidator instanceof ReachableCommitRequestValidator)
            return RequestPolicy.REACHABLE_COMMIT;
        if (requestValidator instanceof TipRequestValidator)
            return RequestPolicy.TIP;
        if (requestValidator instanceof ReachableCommitTipRequestValidator)
            return RequestPolicy.REACHABLE_COMMIT_TIP;
        if (requestValidator instanceof AnyRequestValidator)
            return RequestPolicy.ANY;
        return null;
    }

    public void setRequestPolicy(RequestPolicy policy) {
        switch (policy) {
            case ADVERTISED:
            default:
                requestValidator = new AdvertisedRequestValidator();
                break;
            case REACHABLE_COMMIT:
                requestValidator = new ReachableCommitRequestValidator();
                break;
            case TIP:
                requestValidator = new TipRequestValidator();
                break;
            case REACHABLE_COMMIT_TIP:
                requestValidator = new ReachableCommitTipRequestValidator();
                break;
            case ANY:
                requestValidator = new AnyRequestValidator();
                break;
        }
    }

    public void setRequestValidator(@Nullable RequestValidator validator) {
        requestValidator = validator != null ? validator
                : new AdvertisedRequestValidator();
    }

    public AdvertiseRefsHook getAdvertiseRefsHook() {
        return advertiseRefsHook;
    }

    public RefFilter getRefFilter() {
        return refFilter;
    }

    public void setAdvertiseRefsHook(
            @Nullable AdvertiseRefsHook advertiseRefsHook) {
        this.advertiseRefsHook = advertiseRefsHook != null ? advertiseRefsHook
                : AdvertiseRefsHook.DEFAULT;
    }

    public void setProtocolV2Hook(@Nullable ProtocolV2Hook hook) {
        this.protocolV2Hook = hook != null ? hook : ProtocolV2Hook.DEFAULT;
    }

    public ProtocolV2Hook getProtocolV2Hook() {
        return this.protocolV2Hook != null ? this.protocolV2Hook
                : ProtocolV2Hook.DEFAULT;
    }

    public void setRefFilter(@Nullable RefFilter refFilter) {
        this.refFilter = refFilter != null ? refFilter : RefFilter.DEFAULT;
    }

    public PreUploadHook getPreUploadHook() {
        return preUploadHook;
    }

    public void setPreUploadHook(@Nullable PreUploadHook hook) {
        preUploadHook = hook != null ? hook : PreUploadHook.NULL;
    }

    public PostUploadHook getPostUploadHook() {
        return postUploadHook;
    }

    public void setPostUploadHook(@Nullable PostUploadHook hook) {
        postUploadHook = hook != null ? hook : PostUploadHook.NULL;
    }

    public void setPackConfig(@Nullable PackConfig pc) {
        this.packConfig = pc;
    }

    public void setTransferConfig(@Nullable TransferConfig tc) {
        this.transferConfig = tc != null ? tc : new TransferConfig(db);
        if (transferConfig.isAllowTipSha1InWant()) {
            setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
                    ? RequestPolicy.REACHABLE_COMMIT_TIP : RequestPolicy.TIP);
        } else {
            setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
                    ? RequestPolicy.REACHABLE_COMMIT : RequestPolicy.ADVERTISED);
        }
    }

    public boolean isSideBand() throws RequestNotYetReadException {
        if (currentRequest == null) {
            throw new RequestNotYetReadException();
        }
        Set<String> caps = currentRequest.getClientCapabilities();
        return caps.contains(OPTION_SIDE_BAND)
                || caps.contains(OPTION_SIDE_BAND_64K);
    }

    public void setExtraParameters(Collection<String> params) {
    }

    public void setCachedPackUriProvider(@Nullable CachedPackUriProvider p) {
        cachedPackUriProvider = p;
    }

    private boolean useProtocolV2() {
        return ProtocolVersion.V2.equals(transferConfig.protocolVersion)
                && clientRequestedV2;
    }

    public void upload(InputStream input
                       @Nullable OutputStream messages) throws IOException {
        try {
            uploadWithExceptionPropagation(input
        } catch (ServiceMayNotContinueException err) {
            if (!err.isOutput() && err.getMessage() != null) {
                try {
                    errOut.writeError(err.getMessage());
                } catch (IOException e) {
                    err.addSuppressed(e);
                    throw err;
                }
                err.setOutput();
            }
            throw err;
        } catch (IOException | RuntimeException | Error err) {
            if (rawOut != null) {
                String msg = err instanceof PackProtocolException
                        ? err.getMessage()
                        : JGitText.get().internalServerError;
                try {
                    errOut.writeError(msg);
                } catch (IOException e) {
                    err.addSuppressed(e);
                    throw err;
                }
                throw new UploadPackInternalServerErrorException(err);
            }
            throw err;
        }
    }

    public void uploadWithExceptionPropagation(InputStream input
                                               OutputStream output
            throws ServiceMayNotContinueException
        try {
            rawIn = input;
            if (messages != null) {
                msgOut = messages;
            }

            if (timeout > 0) {
                final Thread caller = Thread.currentThread();
                TimeoutInputStream i = new TimeoutInputStream(rawIn
                @SuppressWarnings("resource")
                TimeoutOutputStream o = new TimeoutOutputStream(output
                i.setTimeout(timeout * 1000);
                o.setTimeout(timeout * 1000);
                rawIn = i;
                output = o;
            }

            rawOut = new ResponseBufferedOutputStream(output);
            if (biDirectionalPipe) {
                rawOut.stopBuffering();
            }

            pckIn = new PacketLineIn(rawIn);
            PacketLineOut pckOut = new PacketLineOut(rawOut);
            if (useProtocolV2()) {
                serviceV2(pckOut);
            } else {
                service(pckOut);
            }
        } finally {
            msgOut = NullOutputStream.INSTANCE;
            walk.close();
            if (timer != null) {
                try {
                    timer.terminate();
                } finally {
                    timer = null;
                }
            }
        }
    }

    public PackStatistics getStatistics() {
        return statistics;
    }

    private Map<String
        if (refs != null) {
            return refs;
        }

        if (!advertiseRefsHookCalled) {
            advertiseRefsHook.advertiseRefs(this);
            advertiseRefsHookCalled = true;
        }
        if (refs == null) {
            setAdvertisedRefs(
                    db.getRefDatabase().getRefs().stream()
                            .collect(toRefMap((a
        }
        return refs;
    }

    private Map<String
            throws IOException {
        if (refPrefixes.isEmpty()) {
            return getAdvertisedOrDefaultRefs();
        }
        if (refs == null && !advertiseRefsHookCalled) {
            advertiseRefsHook.advertiseRefs(this);
            advertiseRefsHookCalled = true;
        }
        if (refs == null) {
            String[] prefixes = refPrefixes.toArray(new String[0]);
            Map<String
                    db.getRefDatabase().getRefsByPrefix(prefixes).stream()
                            .collect(toRefMap((a
            if (refFilter != RefFilter.DEFAULT) {
                return refFilter.filter(rs);
            }
            return transferConfig.getRefFilter().filter(rs);
        }

        return refs.values().stream()
                .filter(ref -> refPrefixes.stream()
                        .anyMatch(ref.getName()::startsWith))
                .collect(toRefMap((a
    }

    @NonNull
    private static Map<String
            Map<String
        return unmodifiableMap(
                names.stream()
                        .map(refs::get)
                        .filter(Objects::nonNull)
                        .collect(toRefMap((a
    }

    @NonNull
    private Map<String
        if (refs != null) {
            return mapRefs(refs
        }
        if (!advertiseRefsHookCalled) {
            advertiseRefsHook.advertiseRefs(this);
            advertiseRefsHookCalled = true;
        }
        if (refs == null &&
                refFilter == RefFilter.DEFAULT &&
                transferConfig.hasDefaultRefFilter()) {
            String[] ns = names.toArray(new String[0]);
            return unmodifiableMap(db.getRefDatabase().exactRef(ns));
        }
        return mapRefs(getAdvertisedOrDefaultRefs()
    }

    @Nullable
    private Ref findRef(String name) throws IOException {
        if (refs != null) {
            return RefDatabase.findRef(refs
        }
        if (!advertiseRefsHookCalled) {
            advertiseRefsHook.advertiseRefs(this);
            advertiseRefsHookCalled = true;
        }
        if (refs == null &&
                refFilter == RefFilter.DEFAULT &&
                transferConfig.hasDefaultRefFilter()) {
            return db.getRefDatabase().findRef(name);
        }
        return RefDatabase.findRef(getAdvertisedOrDefaultRefs()
    }

    private void service(PacketLineOut pckOut) throws IOException {
        boolean sendPack = false;
        PackStatistics.Accumulator accumulator = new PackStatistics.Accumulator();
        List<ObjectId> unshallowCommits = new ArrayList<>();
        FetchRequest req;
        try {
            if (biDirectionalPipe)
                sendAdvertisedRefs(new PacketLineOutRefAdvertiser(pckOut));
            else if (requestValidator instanceof AnyRequestValidator)
                advertised = Collections.emptySet();
            else
                advertised = refIdSet(getAdvertisedOrDefaultRefs().values());

            long negotiateStart = System.currentTimeMillis();
            accumulator.advertised = advertised.size();

            ProtocolV0Parser parser = new ProtocolV0Parser(transferConfig);
            req = parser.recvWants(pckIn);
            currentRequest = req;

            wantIds = req.getWantIds();

            if (req.getWantIds().isEmpty()) {
                preUploadHook.onBeginNegotiateRound(this
                preUploadHook.onEndNegotiateRound(this
                        false);
                return;
            }
            accumulator.wants = req.getWantIds().size();

            if (req.getClientCapabilities().contains(OPTION_MULTI_ACK_DETAILED)) {
                multiAck = MultiAck.DETAILED;
                noDone = req.getClientCapabilities().contains(OPTION_NO_DONE);
            } else if (req.getClientCapabilities().contains(OPTION_MULTI_ACK))
                multiAck = MultiAck.CONTINUE;
            else
                multiAck = MultiAck.OFF;

            if (!req.getClientShallowCommits().isEmpty()) {
                verifyClientShallow(req.getClientShallowCommits());
            }

            if (req.getDepth() != 0 || req.getDeepenSince() != 0) {
                computeShallowsAndUnshallows(req
                }
                    unshallowCommits.add(unshallow);
                }
                pckOut.end();
            }

            if (!req.getClientShallowCommits().isEmpty())
                walk.assumeShallow(req.getClientShallowCommits());
            sendPack = negotiate(req
            accumulator.timeNegotiating += System.currentTimeMillis()
                    - negotiateStart;

            if (sendPack && !biDirectionalPipe) {
                int eof = rawIn.read();
                if (0 <= eof) {
                    sendPack = false;
                    throw new CorruptObjectException(MessageFormat.format(
                            JGitText.get().expectedEOFReceived
                }
            }
        } finally {
            if (!sendPack && !biDirectionalPipe) {
                while (0 < rawIn.skip(2048) || 0 <= rawIn.read()) {
                }
            }
            rawOut.stopBuffering();
        }

        if (sendPack) {
            sendPack(accumulator
                    unshallowCommits
        }
    }

    private void lsRefsV2(PacketLineOut pckOut) throws IOException {
        ProtocolV2Parser parser = new ProtocolV2Parser(transferConfig);
        LsRefsV2Request req = parser.parseLsRefsRequest(pckIn);
        protocolV2Hook.onLsRefs(req);

        rawOut.stopBuffering();
        PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);
        adv.setUseProtocolV2(true);
        if (req.getPeel()) {
            adv.setDerefTags(true);
        }
        Map<String
        if (req.getSymrefs()) {
            findSymrefs(adv
        }

        adv.send(refsToSend.values());
        adv.end();
    }

    private Map<String
            throws IOException {
        Map<String

        List<String> wanted = req.getWantedRefs();
        Map<String

        for (String refName : wanted) {
            Ref ref = resolved.get(refName);
            if (ref == null) {
                throw new PackProtocolException(MessageFormat
                        .format(JGitText.get().invalidRefName
            }
            ObjectId oid = ref.getObjectId();
            if (oid == null) {
                throw new PackProtocolException(MessageFormat
                        .format(JGitText.get().invalidRefName
            }
            result.put(refName
        }
        return result;
    }

    private void fetchV2(PacketLineOut pckOut) throws IOException {
        if (requestValidator instanceof TipRequestValidator ||
                requestValidator instanceof ReachableCommitTipRequestValidator ||
                requestValidator instanceof AnyRequestValidator) {
            advertised = Collections.emptySet();
        } else {
            advertised = refIdSet(getAdvertisedOrDefaultRefs().values());
        }

        PackStatistics.Accumulator accumulator = new PackStatistics.Accumulator();
        ProtocolV2Parser parser = new ProtocolV2Parser(transferConfig);
        FetchV2Request req = parser.parseFetchRequest(pckIn);
        currentRequest = req;
        rawOut.stopBuffering();

        protocolV2Hook.onFetch(req);

        if (req.getSidebandAll()) {
            pckOut.setUsingSideband(true);
        }

        List<ObjectId> deepenNots = new ArrayList<>();
        for (String s : req.getDeepenNotRefs()) {
            Ref ref = findRef(s);
            if (ref == null) {
                throw new PackProtocolException(MessageFormat
                        .format(JGitText.get().invalidRefName
            }
            deepenNots.add(ref.getObjectId());
        }

        Map<String
        req.getWantIds().addAll(wantedRefs.values());
        wantIds = req.getWantIds();

        boolean sectionSent = false;
        boolean mayHaveShallow = req.getDepth() != 0
                || req.getDeepenSince() != 0
                || !req.getDeepenNotRefs().isEmpty();
        List<ObjectId> shallowCommits = new ArrayList<>();
        List<ObjectId> unshallowCommits = new ArrayList<>();

        if (!req.getClientShallowCommits().isEmpty()) {
            verifyClientShallow(req.getClientShallowCommits());
        }
        if (mayHaveShallow) {
            computeShallowsAndUnshallows(req
                    shallowCommit -> shallowCommits.add(shallowCommit)
                    unshallowCommit -> unshallowCommits.add(unshallowCommit)
                    deepenNots);
        }
        if (!req.getClientShallowCommits().isEmpty())
            walk.assumeShallow(req.getClientShallowCommits());

        if (req.wasDoneReceived()) {
            processHaveLines(req.getPeerHas()
                    new PacketLineOut(NullOutputStream.INSTANCE)
                    accumulator);
        } else {
            for (ObjectId id : req.getPeerHas()) {
                if (walk.getObjectReader().has(id)) {
                }
            }
            processHaveLines(req.getPeerHas()
                    new PacketLineOut(NullOutputStream.INSTANCE)
                    accumulator);
            if (okToGiveUp()) {
            } else if (commonBase.isEmpty()) {
            }
            sectionSent = true;
        }

        if (req.wasDoneReceived() || okToGiveUp()) {
            if (mayHaveShallow) {
                if (sectionSent)
                    pckOut.writeDelim();
                for (ObjectId o : shallowCommits) {
                }
                for (ObjectId o : unshallowCommits) {
                }
                sectionSent = true;
            }

            if (!wantedRefs.isEmpty()) {
                if (sectionSent) {
                    pckOut.writeDelim();
                }
                for (Map.Entry<String
                        wantedRefs.entrySet()) {
                    pckOut.writeString(entry.getValue().getName() + ' ' +
                            entry.getKey() + '\n');
                }
                sectionSent = true;
            }

            if (sectionSent)
                pckOut.writeDelim();
            if (!pckOut.isUsingSideband()) {
            }

            sendPack(accumulator
                    req
                    req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)
                            ? db.getRefDatabase().getRefsByPrefix(R_TAGS)
                            : null
                    unshallowCommits
        } else {
            pckOut.end();
        }
    }

    private boolean serveOneCommandV2(PacketLineOut pckOut) throws IOException {
        String command;
        try {
            command = pckIn.readString();
        } catch (EOFException eof) {
            return true;
        }
        if (PacketLineIn.isEnd(command)) {
            return true;
        }
            lsRefsV2(pckOut);
            return false;
        }
            fetchV2(pckOut);
            return false;
        }
        throw new PackProtocolException(MessageFormat
                .format(JGitText.get().unknownTransportCommand
    }

    @SuppressWarnings("nls")
    private List<String> getV2CapabilityAdvertisement() {
        ArrayList<String> caps = new ArrayList<>();
        caps.add("version 2");
        caps.add(COMMAND_LS_REFS);
        boolean advertiseRefInWant = transferConfig.isAllowRefInWant()
                && db.getConfig().getBoolean("uploadpack"
                "advertiserefinwant"
        caps.add(COMMAND_FETCH + '='
                + (transferConfig.isAllowFilter() ? OPTION_FILTER + ' ' : "")
                + (advertiseRefInWant ? CAPABILITY_REF_IN_WANT + ' ' : "")
                + (transferConfig.isAdvertiseSidebandAll()
                ? OPTION_SIDEBAND_ALL + ' '
                : "")
                + (cachedPackUriProvider != null ? "packfile-uris " : "")
                + OPTION_SHALLOW);
        caps.add(CAPABILITY_SERVER_OPTION);
        return caps;
    }

    private void serviceV2(PacketLineOut pckOut) throws IOException {
        if (biDirectionalPipe) {
            protocolV2Hook
                    .onCapabilities(CapabilitiesV2Request.builder().build());
            for (String s : getV2CapabilityAdvertisement()) {
            }
            pckOut.end();

            while (!serveOneCommandV2(pckOut)) {
            }
            return;
        }

        try {
            serveOneCommandV2(pckOut);
        } finally {
            while (0 < rawIn.skip(2048) || 0 <= rawIn.read()) {
            }
            rawOut.stopBuffering();
        }
    }

    private static Set<ObjectId> refIdSet(Collection<Ref> refs) {
        Set<ObjectId> ids = new HashSet<>(refs.size());
        for (Ref ref : refs) {
            ObjectId id = ref.getObjectId();
            if (id != null) {
                ids.add(id);
            }
            id = ref.getPeeledObjectId();
            if (id != null) {
                ids.add(id);
            }
        }
        return ids;
    }

    private void computeShallowsAndUnshallows(FetchRequest req
                                              IOConsumer<ObjectId> shallowFunc
                                              IOConsumer<ObjectId> unshallowFunc
                                              List<ObjectId> deepenNots)
            throws IOException {
        if (req.getClientCapabilities().contains(OPTION_DEEPEN_RELATIVE)) {
            throw new UnsupportedOperationException();
        }

        int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
                : req.getDepth() - 1;
        try (DepthWalk.RevWalk depthWalk = new DepthWalk.RevWalk(
                walk.getObjectReader()

            depthWalk.setDeepenSince(req.getDeepenSince());

            for (ObjectId o : req.getWantIds()) {
                try {
                    depthWalk.markRoot(depthWalk.parseCommit(o));
                } catch (IncorrectObjectTypeException notCommit) {
                }
            }

            depthWalk.setDeepenNots(deepenNots);

            RevCommit o;
            boolean atLeastOne = false;
            while ((o = depthWalk.next()) != null) {
                DepthWalk.Commit c = (DepthWalk.Commit) o;
                atLeastOne = true;

                boolean isBoundary = (c.getDepth() == walkDepth) || c.isBoundary();

                if (isBoundary && !req.getClientShallowCommits().contains(c)) {
                    shallowFunc.accept(c.copy());
                }

                if (!isBoundary && req.getClientShallowCommits().remove(c)) {
                    unshallowFunc.accept(c.copy());
                }
            }
            if (!atLeastOne) {
                throw new PackProtocolException(
                        JGitText.get().noCommitsSelectedForShallow);
            }
        }
    }

    private void verifyClientShallow(Set<ObjectId> shallowCommits)
            throws IOException
        AsyncRevObjectQueue q = walk.parseAny(shallowCommits
        try {
            for (; ; ) {
                try {
                    RevObject o = q.next();
                    if (o == null) {
                        break;
                    }
                    if (!(o instanceof RevCommit)) {
                        throw new PackProtocolException(
                                MessageFormat.format(
                                        JGitText.get().invalidShallowObject
                                        o.name()));
                    }
                } catch (MissingObjectException notCommit) {
                    shallowCommits.remove(notCommit.getObjectId());
                    continue;
                }
            }
        } finally {
            q.release();
        }
    }

    public void sendAdvertisedRefs(RefAdvertiser adv) throws IOException
            ServiceMayNotContinueException {
        sendAdvertisedRefs(adv
    }

    public void sendAdvertisedRefs(RefAdvertiser adv
                                   @Nullable String serviceName) throws IOException
            ServiceMayNotContinueException {
        if (useProtocolV2()) {
            protocolV2Hook
                    .onCapabilities(CapabilitiesV2Request.builder().build());
            for (String s : getV2CapabilityAdvertisement()) {
                adv.writeOne(s);
            }
            adv.end();
            return;
        }

        Map<String

        if (serviceName != null) {
            adv.end();
        }
        adv.init(db);
        adv.advertiseCapability(OPTION_INCLUDE_TAG);
        adv.advertiseCapability(OPTION_MULTI_ACK_DETAILED);
        adv.advertiseCapability(OPTION_MULTI_ACK);
        adv.advertiseCapability(OPTION_OFS_DELTA);
        adv.advertiseCapability(OPTION_SIDE_BAND);
        adv.advertiseCapability(OPTION_SIDE_BAND_64K);
        adv.advertiseCapability(OPTION_THIN_PACK);
        adv.advertiseCapability(OPTION_NO_PROGRESS);
        adv.advertiseCapability(OPTION_SHALLOW);
        if (!biDirectionalPipe)
            adv.advertiseCapability(OPTION_NO_DONE);
        RequestPolicy policy = getRequestPolicy();
        if (policy == RequestPolicy.TIP
                || policy == RequestPolicy.REACHABLE_COMMIT_TIP
                || policy == null)
            adv.advertiseCapability(OPTION_ALLOW_TIP_SHA1_IN_WANT);
        if (policy == RequestPolicy.REACHABLE_COMMIT
                || policy == RequestPolicy.REACHABLE_COMMIT_TIP
                || policy == null)
            adv.advertiseCapability(OPTION_ALLOW_REACHABLE_SHA1_IN_WANT);
        adv.advertiseCapability(OPTION_AGENT
        if (transferConfig.isAllowFilter()) {
            adv.advertiseCapability(OPTION_FILTER);
        }
        adv.setDerefTags(true);
        findSymrefs(adv
        advertised = adv.send(advertisedOrDefaultRefs.values());

        if (adv.isEmpty())
            adv.advertiseId(ObjectId.zeroId()
        adv.end();
    }

    public void sendMessage(String what) {
        try {
        } catch (IOException e) {
        }
    }

    public OutputStream getMessageOutputStream() {
        return msgOut;
    }

    public int getDepth() {
        if (currentRequest == null)
            throw new RequestNotYetReadException();
        return currentRequest.getDepth();
    }

    @Deprecated
    public final long getFilterBlobLimit() {
        return getFilterSpec().getBlobLimit();
    }

    public final FilterSpec getFilterSpec() {
        if (currentRequest == null) {
            throw new RequestNotYetReadException();
        }
        return currentRequest.getFilterSpec();
    }

    public String getPeerUserAgent() {
        if (currentRequest != null && currentRequest.getAgent() != null) {
            return currentRequest.getAgent();
        }

        return userAgent;
    }

    private boolean negotiate(FetchRequest req
                              PackStatistics.Accumulator accumulator
                              PacketLineOut pckOut)
            throws IOException {
        okToGiveUp = Boolean.FALSE;

        ObjectId last = ObjectId.zeroId();
        List<ObjectId> peerHas = new ArrayList<>(64);
        for (; ; ) {
            String line;
            try {
                line = pckIn.readString();
            } catch (EOFException eof) {
                if (!biDirectionalPipe && req.getDepth() > 0)
                    return false;
                throw eof;
            }

            if (PacketLineIn.isEnd(line)) {
                last = processHaveLines(peerHas
                if (commonBase.isEmpty() || multiAck != MultiAck.OFF)
                if (noDone && sentReady) {
                    return true;
                }
                if (!biDirectionalPipe)
                    return false;
                pckOut.flush();

                peerHas.add(ObjectId.fromString(line.substring(5)));
                accumulator.haves++;
                last = processHaveLines(peerHas

                if (commonBase.isEmpty())

                else if (multiAck != MultiAck.OFF)

                return true;

            } else {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot
            }
        }
    }

    private ObjectId processHaveLines(List<ObjectId> peerHas
                                      PackStatistics.Accumulator accumulator)
            throws IOException {
        preUploadHook.onBeginNegotiateRound(this
        if (wantAll.isEmpty() && !wantIds.isEmpty())
            parseWants(accumulator);
        if (peerHas.isEmpty())
            return last;

        sentReady = false;
        int haveCnt = 0;
        walk.getObjectReader().setAvoidUnreachableObjects(true);
        AsyncRevObjectQueue q = walk.parseAny(peerHas
        try {
            for (; ; ) {
                RevObject obj;
                try {
                    obj = q.next();
                } catch (MissingObjectException notFound) {
                    continue;
                }
                if (obj == null)
                    break;

                last = obj;
                haveCnt++;

                if (obj instanceof RevCommit) {
                    RevCommit c = (RevCommit) obj;
                    if (oldestTime == 0 || c.getCommitTime() < oldestTime)
                        oldestTime = c.getCommitTime();
                }

                if (obj.has(PEER_HAS))
                    continue;

                obj.add(PEER_HAS);
                if (obj instanceof RevCommit)
                    ((RevCommit) obj).carry(PEER_HAS);
                addCommonBase(obj);

                switch (multiAck) {
                    case OFF:
                        if (commonBase.size() == 1)
                        break;
                    case CONTINUE:
                        break;
                    case DETAILED:
                        break;
                }
            }
        } finally {
            q.release();
            walk.getObjectReader().setAvoidUnreachableObjects(false);
        }

        int missCnt = peerHas.size() - haveCnt;

        boolean didOkToGiveUp = false;
        if (0 < missCnt) {
            for (int i = peerHas.size() - 1; i >= 0; i--) {
                ObjectId id = peerHas.get(i);
                if (walk.lookupOrNull(id) == null) {
                    didOkToGiveUp = true;
                    if (okToGiveUp()) {
                        switch (multiAck) {
                            case OFF:
                                break;
                            case CONTINUE:
                                break;
                            case DETAILED:
                                sentReady = true;
                                break;
                        }
                    }
                    break;
                }
            }
        }

        if (multiAck == MultiAck.DETAILED && !didOkToGiveUp && okToGiveUp()) {
            ObjectId id = peerHas.get(peerHas.size() - 1);
            sentReady = true;
        }

        preUploadHook.onEndNegotiateRound(this
        peerHas.clear();
        return last;
    }

    private void parseWants(PackStatistics.Accumulator accumulator) throws IOException {
        List<ObjectId> notAdvertisedWants = null;
        for (ObjectId obj : wantIds) {
            if (!advertised.contains(obj)) {
                if (notAdvertisedWants == null)
                    notAdvertisedWants = new ArrayList<>();
                notAdvertisedWants.add(obj);
            }
        }
        if (notAdvertisedWants != null) {
            accumulator.notAdvertisedWants = notAdvertisedWants.size();

            long startReachabilityChecking = System.currentTimeMillis();

            requestValidator.checkWants(this

            accumulator.reachabilityCheckDuration = System.currentTimeMillis() -
                    startReachabilityChecking;
        }

        AsyncRevObjectQueue q = walk.parseAny(wantIds
        try {
            RevObject obj;
            while ((obj = q.next()) != null) {
                want(obj);

                if (!(obj instanceof RevCommit))
                    obj.add(SATISFIED);
                if (obj instanceof RevTag) {
                    obj = walk.peel(obj);
                    if (obj instanceof RevCommit)
                        want(obj);
                }
            }
            wantIds.clear();
        } catch (MissingObjectException notFound) {
            throw new WantNotValidException(notFound.getObjectId()
        } finally {
            q.release();
        }
    }

    private void want(RevObject obj) {
        if (!obj.has(WANT)) {
            obj.add(WANT);
            wantAll.add(obj);
        }
    }

    public static final class AdvertisedRequestValidator
            implements RequestValidator {
        @Override
        public void checkWants(UploadPack up
                throws PackProtocolException
            if (!up.isBiDirectionalPipe())
                new ReachableCommitRequestValidator().checkWants(up
            else if (!wants.isEmpty())
                throw new WantNotValidException(wants.iterator().next());
        }
    }

    public static final class ReachableCommitRequestValidator
            implements RequestValidator {
        @Override
        public void checkWants(UploadPack up
                throws PackProtocolException
            checkNotAdvertisedWants(up
        }
    }

    public static final class TipRequestValidator implements RequestValidator {
        @Override
        public void checkWants(UploadPack up
                throws PackProtocolException
            if (!up.isBiDirectionalPipe())
                new ReachableCommitTipRequestValidator().checkWants(up
            else if (!wants.isEmpty()) {
                Set<ObjectId> refIds =
                        refIdSet(up.getRepository().getRefDatabase().getRefs());
                for (ObjectId obj : wants) {
                    if (!refIds.contains(obj))
                        throw new WantNotValidException(obj);
                }
            }
        }
    }

    public static final class ReachableCommitTipRequestValidator
            implements RequestValidator {
        @Override
        public void checkWants(UploadPack up
                throws PackProtocolException
            checkNotAdvertisedWants(up
                    up.getRepository().getRefDatabase().getRefs());
        }
    }

    public static final class AnyRequestValidator implements RequestValidator {
        @Override
        public void checkWants(UploadPack up
                throws PackProtocolException
        }
    }

    private static void checkNotAdvertisedWants(UploadPack up
                                                List<ObjectId> notAdvertisedWants
            throws IOException {

        ObjectReader reader = up.getRevWalk().getObjectReader();

        try (RevWalk walk = new RevWalk(reader)) {
            walk.setRetainBody(false);
            List<RevObject> wantsAsObjs = objectIdsToRevObjects(walk
                    notAdvertisedWants);
            List<RevCommit> wantsAsCommits = wantsAsObjs.stream()
                    .filter(obj -> obj instanceof RevCommit)
                    .map(obj -> (RevCommit) obj)
                    .collect(Collectors.toList());
            boolean allWantsAreCommits = wantsAsObjs.size() == wantsAsCommits
                    .size();
            boolean repoHasBitmaps = reader.getBitmapIndex() != null;

            if (!allWantsAreCommits) {
                if (!repoHasBitmaps && !up.transferConfig.isAllowFilter()) {
                    RevObject nonCommit = wantsAsObjs
                            .stream()
                            .filter(obj -> !(obj instanceof RevCommit))
                            .limit(1)
                            .collect(Collectors.toList()).get(0);
                    throw new WantNotValidException(nonCommit);
                }

                try (ObjectWalk objWalk = walk.toObjectWalkWithSameObjects()) {
                    Stream<RevObject> startersAsObjs = importantRefsFirst(visibleRefs)
                            .map(UploadPack::refToObjectId)
                            .map(objId -> objectIdToRevObject(objWalk

                    ObjectReachabilityChecker reachabilityChecker = objWalk
                            .createObjectReachabilityChecker();
                    Optional<RevObject> unreachable = reachabilityChecker
                            .areAllReachable(wantsAsObjs
                    if (unreachable.isPresent()) {
                        throw new WantNotValidException(unreachable.get());
                    }
                }
                return;
            }

            ReachabilityChecker reachabilityChecker = walk
                    .createReachabilityChecker();

            Stream<RevCommit> reachableCommits = importantRefsFirst(visibleRefs)
                    .map(UploadPack::refToObjectId)
                    .map(objId -> objectIdToRevCommit(walk

            Optional<RevCommit> unreachable = reachabilityChecker
                    .areAllReachable(wantsAsCommits
            if (unreachable.isPresent()) {
                throw new WantNotValidException(unreachable.get());
            }

        } catch (MissingObjectException notFound) {
            throw new WantNotValidException(notFound.getObjectId()
        }
    }

    static Stream<Ref> importantRefsFirst(
            Collection<Ref> visibleRefs) {
        Predicate<Ref> startsWithRefsHeads = ref -> ref.getName()
                .startsWith(Constants.R_HEADS);
        Predicate<Ref> startsWithRefsTags = ref -> ref.getName()
                .startsWith(Constants.R_TAGS);
        Predicate<Ref> allOther = ref -> !startsWithRefsHeads.test(ref)
                && !startsWithRefsTags.test(ref);

        return Stream.concat(
                visibleRefs.stream().filter(startsWithRefsHeads)
                Stream.concat(
                        visibleRefs.stream().filter(startsWithRefsTags)
                        visibleRefs.stream().filter(allOther)));
    }

    private static ObjectId refToObjectId(Ref ref) {
        return ref.getObjectId() != null ? ref.getObjectId()
                : ref.getPeeledObjectId();
    }

    @Nullable
    private static RevCommit objectIdToRevCommit(RevWalk walk
                                                 ObjectId objectId) {
        if (objectId == null) {
            return null;
        }

        try {
            return walk.parseCommit(objectId);
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    private static RevObject objectIdToRevObject(RevWalk walk
                                                 ObjectId objectId) {
        if (objectId == null) {
            return null;
        }

        try {
            return walk.parseAny(objectId);
        } catch (IOException e) {
            return null;
        }
    }

    private static List<RevObject> objectIdsToRevObjects(RevWalk walk
                                                         Iterable<ObjectId> objectIds)
            throws MissingObjectException
        List<RevObject> result = new ArrayList<>();
        for (ObjectId objectId : objectIds) {
            result.add(walk.parseAny(objectId));
        }
        return result;
    }

    private void addCommonBase(RevObject o) {
        if (!o.has(COMMON)) {
            o.add(COMMON);
            commonBase.add(o);
            okToGiveUp = null;
        }
    }

    private boolean okToGiveUp() throws PackProtocolException {
        if (okToGiveUp == null)
            okToGiveUp = Boolean.valueOf(okToGiveUpImp());
        return okToGiveUp.booleanValue();
    }

    private boolean okToGiveUpImp() throws PackProtocolException {
        if (commonBase.isEmpty())
            return false;

        try {
            for (RevObject obj : wantAll) {
                if (!wantSatisfied(obj))
                    return false;
            }
            return true;
        } catch (IOException e) {
            throw new PackProtocolException(JGitText.get().internalRevisionError
        }
    }

    private boolean wantSatisfied(RevObject want) throws IOException {
        if (want.has(SATISFIED))
            return true;

        walk.resetRetain(SAVE);
        walk.markStart((RevCommit) want);
        if (oldestTime != 0)
            walk.setRevFilter(CommitTimeRevFilter.after(oldestTime * 1000L));
        for (; ; ) {
            final RevCommit c = walk.next();
            if (c == null)
                break;
            if (c.has(PEER_HAS)) {
                addCommonBase(c);
                want.add(SATISFIED);
                return true;
            }
        }
        return false;
    }

    private void sendPack(PackStatistics.Accumulator accumulator
                          FetchRequest req
                          @Nullable Collection<Ref> allTags
                          List<ObjectId> unshallowCommits
                          List<ObjectId> deepenNots
                          PacketLineOut pckOut) throws IOException {
        Set<String> caps = req.getClientCapabilities();
        boolean sideband = caps.contains(OPTION_SIDE_BAND)
                || caps.contains(OPTION_SIDE_BAND_64K);

        if (sideband) {
            errOut = new SideBandErrorWriter();

            int bufsz = SideBandOutputStream.SMALL_BUF;
            if (req.getClientCapabilities().contains(OPTION_SIDE_BAND_64K)) {
                bufsz = SideBandOutputStream.MAX_BUF;
            }
            OutputStream packOut = new SideBandOutputStream(
                    SideBandOutputStream.CH_DATA

            ProgressMonitor pm = NullProgressMonitor.INSTANCE;
            if (!req.getClientCapabilities().contains(OPTION_NO_PROGRESS)) {
                msgOut = new SideBandOutputStream(
                        SideBandOutputStream.CH_PROGRESS
                pm = new SideBandProgressMonitor(msgOut);
            }

            sendPack(pm
                    unshallowCommits
            pckOut.end();
        } else {
            sendPack(NullProgressMonitor.INSTANCE
                    accumulator
        }
    }

    private void sendPack(ProgressMonitor pm
                          OutputStream packOut
                          PackStatistics.Accumulator accumulator
                          @Nullable Collection<Ref> allTags
                          List<ObjectId> deepenNots) throws IOException {
        if (wantAll.isEmpty()) {
            preUploadHook.onSendPack(this
        } else {
            preUploadHook.onSendPack(this
        }
        msgOut.flush();

        advertised = null;
        refs = null;

        PackConfig cfg = packConfig;
        if (cfg == null)
            cfg = new PackConfig(db);
        final PackWriter pw = new PackWriter(cfg
                accumulator);
        try {
            pw.setIndexDisabled(true);
            if (req.getFilterSpec().isNoOp()) {
                pw.setUseCachedPacks(true);
            } else {
                pw.setFilterSpec(req.getFilterSpec());
                pw.setUseCachedPacks(false);
            }
            pw.setUseBitmaps(
                    req.getDepth() == 0
                            && req.getClientShallowCommits().isEmpty()
                            && req.getFilterSpec().getTreeDepthLimit() == -1);
            pw.setClientShallowCommits(req.getClientShallowCommits());
            pw.setReuseDeltaCommits(true);
            pw.setDeltaBaseAsOffset(
                    req.getClientCapabilities().contains(OPTION_OFS_DELTA));
            pw.setThin(req.getClientCapabilities().contains(OPTION_THIN_PACK));
            pw.setReuseValidatingObjects(false);

            if (commonBase.isEmpty() && refs != null) {
                Set<ObjectId> tagTargets = new HashSet<>();
                for (Ref ref : refs.values()) {
                    if (ref.getPeeledObjectId() != null)
                        tagTargets.add(ref.getPeeledObjectId());
                    else if (ref.getObjectId() == null)
                        continue;
                    else if (ref.getName().startsWith(Constants.R_HEADS))
                        tagTargets.add(ref.getObjectId());
                }
                pw.setTagTargets(tagTargets);
            }

            RevWalk rw = walk;
            if (req.getDepth() > 0 || req.getDeepenSince() != 0 || !deepenNots.isEmpty()) {
                int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
                        : req.getDepth() - 1;
                pw.setShallowPack(req.getDepth()

                        DepthWalk.RevWalk dw = new DepthWalk.RevWalk(
                        walk.getObjectReader()
                dw.setDeepenSince(req.getDeepenSince());
                dw.setDeepenNots(deepenNots);
                dw.assumeShallow(req.getClientShallowCommits());
                rw = dw;
            }

            if (wantAll.isEmpty()) {
                pw.preparePack(pm
                        req.getClientShallowCommits());
            } else {
                walk.reset();

                ObjectWalk ow = rw.toObjectWalkWithSameObjects();
                pw.preparePack(pm
                rw = ow;
            }

            if (req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)
                    && allTags != null) {
                for (Ref ref : allTags) {
                    ObjectId objectId = ref.getObjectId();
                    if (objectId == null) {
                        continue;
                    }

                    if (wantAll.isEmpty()) {
                        if (wantIds.contains(objectId))
                            continue;
                    } else {
                        RevObject obj = rw.lookupOrNull(objectId);
                        if (obj != null && obj.has(WANT))
                            continue;
                    }

                    if (!ref.isPeeled())
                        ref = db.getRefDatabase().peel(ref);

                    ObjectId peeledId = ref.getPeeledObjectId();
                    objectId = ref.getObjectId();
                    if (peeledId == null || objectId == null)
                        continue;

                    objectId = ref.getObjectId();
                    if (pw.willInclude(peeledId) && !pw.willInclude(objectId)) {
                        RevObject o = rw.parseAny(objectId);
                        addTagChain(o
                        pw.addObject(o);
                    }
                }
            }

            if (pckOut.isUsingSideband()) {
                if (req instanceof FetchV2Request &&
                        cachedPackUriProvider != null &&
                        !((FetchV2Request) req).getPackfileUriProtocols().isEmpty()) {
                    FetchV2Request reqV2 = (FetchV2Request) req;
                    pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(
                            pckOut
                            reqV2.getPackfileUriProtocols()
                            cachedPackUriProvider));
                } else {
                }
            }
            pw.writePack(pm

            if (msgOut != NullOutputStream.INSTANCE) {
                String msg = pw.getStatistics().getMessage() + '\n';
                msgOut.write(Constants.encode(msg));
                msgOut.flush();
            }

        } finally {
            statistics = pw.getStatistics();
            if (statistics != null) {
                postUploadHook.onPostUpload(statistics);
            }
            pw.close();
        }
    }

    private static void findSymrefs(
            final RefAdvertiser adv
        Ref head = refs.get(Constants.HEAD);
        if (head != null && head.isSymbolic()) {
            adv.addSymref(Constants.HEAD
        }
    }

    private void addTagChain(
            RevObject o
        while (Constants.OBJ_TAG == o.getType()) {
            RevTag t = (RevTag) o;
            o = t.getObject();
            if (o.getType() == Constants.OBJ_TAG && !pw.willInclude(o.getId())) {
                walk.parseBody(o);
                pw.addObject(o);
            }
        }
    }

    private static class ResponseBufferedOutputStream extends OutputStream {
        private final OutputStream rawOut;

        private OutputStream out;

        ResponseBufferedOutputStream(OutputStream rawOut) {
            this.rawOut = rawOut;
            this.out = new ByteArrayOutputStream();
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            out.write(b);
        }

        @Override
        public void write(byte[] b
            out.write(b
        }

        @Override
        public void flush() throws IOException {
            out.flush();
        }

        @Override
        public void close() throws IOException {
            out.close();
        }

        void stopBuffering() throws IOException {
            if (out != rawOut) {
                ((ByteArrayOutputStream) out).writeTo(rawOut);
                out = rawOut;
            }
        }
    }

    private interface ErrorWriter {
        void writeError(String message) throws IOException;
    }

    private class SideBandErrorWriter implements ErrorWriter {
        @Override
        public void writeError(String message) throws IOException {
            SideBandOutputStream err = new SideBandOutputStream(
                    SideBandOutputStream.CH_ERROR
                    SideBandOutputStream.SMALL_BUF
            err.write(Constants.encode(message));
            err.flush();
        }
    }

    private class PackProtocolErrorWriter implements ErrorWriter {
        @Override
        public void writeError(String message) throws IOException {
            new PacketLineOut(requireNonNull(rawOut))
        }
    }
