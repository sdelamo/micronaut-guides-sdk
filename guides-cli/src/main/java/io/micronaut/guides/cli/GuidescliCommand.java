package io.micronaut.guides.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

@Command(name = "guidescli", description = "...",
        mixinStandardHelpOptions = true)
public class GuidescliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Option(names = {"-i", "--input"}, description = "...")
    File input;

    @Option(names = {"-o", "--output"}, description = "...")
    File output;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(GuidescliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
