package org.eclipse.jgit.benchmarks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.util.FileUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class CreateFileSnapshotBenchmark {

	Path path;

	Path testDir;

	@Setup
	public void setupBenchmark() throws IOException {
		testDir = Files.createTempDirectory("dir");
		path = testDir.resolve("toSnapshot");
		Files.createFile(path);
	}

	@TearDown
	public void teardown() throws IOException {
		FileUtils.delete(testDir.toFile()
	}

	@Benchmark
	@BenchmarkMode({ Mode.AverageTime })
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5
    @Measurement(iterations = 5
	public Path testCreateFile() throws IOException {
		return Files.createTempFile(testDir
	}

	@Benchmark
	@BenchmarkMode({ Mode.AverageTime })
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public FileSnapshot testCreateFileSnapshot() {
		return FileSnapshot.save(path.toFile());
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(CreateFileSnapshotBenchmark.class.getSimpleName())
				.forks(1).jvmArgs("-ea").build();
		new Runner(opt).run();
	}
}
