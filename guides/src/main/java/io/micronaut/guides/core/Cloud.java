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

import io.micronaut.core.order.Ordered;

/**
 * Cloud is an enumeration that represents different cloud providers.
 */
public enum Cloud implements Ordered {
    OCI("Oracle Cloud", "OCI", 1),
    AWS("Amazon Web Services", "AWS", 2),
    AZURE("Microsoft Azure", "Azure", 3),
    GCP("Google Cloud Platform", "GCP", 4);

    private final String accronym;
    private final String name;
    private final int order;

    /**
     * Constructs a Cloud enum with the specified name, acronym, and order.
     *
     * @param name     the name of the cloud provider
     * @param accronym the acronym of the cloud provider
     * @param order    the order of the cloud provider
     */
    Cloud(String name, String accronym, int order) {
        this.name = name;
        this.accronym = accronym;
        this.order = order;
    }

    /**
     * Gets the name of the cloud provider.
     *
     * @return the name of the cloud provider
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the acronym of the cloud provider.
     *
     * @return the acronym of the cloud provider
     */
    public String getAccronym() {
        return accronym;
    }

    /**
     * Returns the acronym of the cloud provider as a string representation.
     *
     * @return the acronym of the cloud provider
     */
    @Override
    public String toString() {
        return accronym;
    }

    /**
     * Gets the order of the cloud provider.
     *
     * @return the order of the cloud provider
     */
    @Override
    public int getOrder() {
        return order;
    }
}
