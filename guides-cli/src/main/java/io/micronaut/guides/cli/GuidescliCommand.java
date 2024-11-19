package io.micronaut.guides.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import io.micronaut.guides.core.GuideProjectGenerator;
import io.micronaut.guides.core.WebsiteGenerator;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;

@Command(name = "guidescli", description = "...",
        mixinStandardHelpOptions = true)
public class GuidescliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Option(names = {"-i", "--input"}, description = "folder contain the tutorials")
    File input;

    @Option(names = {"-o", "--output"}, description = "folder where the website should be generated into")
    File output;

    @Inject
    WebsiteGenerator websiteGenerator;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(GuidescliCommand.class, args);
    }

    public void run() {
        try {
            websiteGenerator.generate(input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
