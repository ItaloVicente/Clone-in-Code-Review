load("@rules_java//java:defs.bzl", "java_library")

package(default_visibility = ["//visibility:public"])

INSECURE_CIPHER_FACTORY = [
    "src/org/eclipse/jgit/transport/InsecureCipherFactory.java",
]

SRCS = glob(
    ["src/**/*.java"],
    exclude = INSECURE_CIPHER_FACTORY,
)

RESOURCES = glob(["resources/**"])

java_library(
    name = "jgit",
    srcs = SRCS,
    javacopts = select({
        "//:jdk9": ["--add-modules=java.xml.bind"],
        "//conditions:default": [],
    }),
    resource_strip_prefix = "org.eclipse.jgit/resources",
    resources = RESOURCES,
    deps = [
        ":insecure_cipher_factory",
        "//lib:bcpg",
        "//lib:bcpkix",
        "//lib:bcprov",
        "//lib:javaewah",
        "//lib:jsch",
        "//lib:jzlib",
        "//lib:slf4j-api",
    ],
)

java_library(
    name = "insecure_cipher_factory",
    srcs = INSECURE_CIPHER_FACTORY,
    javacopts = ["-Xep:InsecureCryptoUsage:OFF"],
)
