SRCS = glob(['src/**'])
RESOURCES = glob(['resources/**'])

java_library(
  name = 'jgit',
  srcs = SRCS,
  resources = RESOURCES,
  deps = [
  ],
  visibility = ['PUBLIC'],
)

java_sources(
  name = 'jgit_src',
  srcs = SRCS + RESOURCES,
)
