http_archive(
    name = "bazel_skylib",
    sha256 = "2ea8a5ed2b448baf4a6855d3ce049c4c452a6470b1efd1504fdb7c1c134d220a",
    strip_prefix = "bazel-skylib-0.8.0",
)

# Check Bazel version when invoked by Bazel directly

bazelisk_version(name = "bazelisk_version")


check_bazel_version()

