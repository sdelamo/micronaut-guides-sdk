/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.guides.core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * CopyFileVisitor is a class that extends SimpleFileVisitor to copy files and directories
 * from a source path to a target path.
 */
public class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private final Path targetPath;
    private Path sourcePath;

    /**
     * Constructs a new CopyFileVisitor with the specified target path.
     *
     * @param targetPath the target path where files and directories will be copied
     */
    public CopyFileVisitor(Path targetPath) {
        this.targetPath = targetPath;
    }

    /**
     * Invoked for a directory before entries in the directory are visited.
     * Creates the corresponding directory in the target path.
     *
     * @param dir   the directory about to be visited
     * @param attrs the directory's basic attributes
     * @return the visit result
     * @throws IOException if an I/O error occurs
     */
    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        if (sourcePath == null) {
            sourcePath = dir;
        } else {
            Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
        }
        return CONTINUE;
    }

    /**
     * Invoked for a file in a directory.
     * Copies the file to the corresponding location in the target path.
     *
     * @param file  the file being visited
     * @param attrs the file's basic attributes
     * @return the visit result
     * @throws IOException if an I/O error occurs
     */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Files.copy(file, targetPath.resolve(sourcePath.relativize(file)), REPLACE_EXISTING);
        return CONTINUE;
    }
}
